package com.hackerearth.fullstack.backend.controller;

import com.hackerearth.fullstack.backend.exception.CustomException;
import com.hackerearth.fullstack.backend.model.Student;
import com.hackerearth.fullstack.backend.payload.request.StudentRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.hackerearth.fullstack.backend.utils.Constants.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class StudentControllerTest {
    @Autowired
    StudentController studentController;

    @Test()
    void testGivenNoStudentNameProvided_WhenAddStudent_ThenThrowCustomException() {
        // given
        StudentRequest studentRequest = new StudentRequest(null, "14BCA4225");

        // when
        Exception exception = assertThrows(CustomException.class, () -> {
            studentController.addStudent(studentRequest);
        });

        // then
        then(exception.getMessage()).isEqualTo(INVALID_NAME_MESSAGE);
    }

    @Test()
    void testGivenNoRollNumberProvided_WhenAddStudent_ThenThrowCustomException() {
        // given
        StudentRequest studentRequest = new StudentRequest("Arfath", null);

        // when
        Exception exception = assertThrows(CustomException.class, () -> {
            studentController.addStudent(studentRequest);
        });

        // then
        then(exception.getMessage()).isEqualTo(INVALID_ROLL_MESSAGE);
    }

    @Test
    void testGivenNoStudentsCreated_WhenGetAllStudents_ThenShouldResolveEmptyList() {
        // given

        // when
        Iterable<Student> students = studentController.getAllStudents();

        // then
        then(students.iterator().hasNext()).isEqualTo(false);
    }

    @Test
    @DirtiesContext
    void testGivenStudentIsCreated_WhenGetAllStudents_ThenShouldResolveWithStudent() throws Exception {
        // given
        studentController.addStudent(new StudentRequest("Arfath", "14BCA4225"));

        // when
        Iterable<Student> students = studentController.getAllStudents();

        // then
        then(students.iterator().hasNext()).isEqualTo(true);
        then(students.iterator().next().getName()).isEqualTo("Arfath");
    }

    @Test
    @DirtiesContext
    void testGivenStudentIsNotPresent_WhenDeleteStudent_ThenShouldThrowCustomException() {
        // given
        long studentId = 1L;

        // when
        Exception exception = assertThrows(CustomException.class, () -> {
            studentController.deleteStudent(studentId);
        });

        // then
        then(exception.getMessage()).isEqualTo(NO_SUCH_STUDENT_MESSAGE);
    }

    @Test
    @DirtiesContext
    void testGivenStudentIsPresent_WhenDeleteStudent_ThenShouldThrowCustomException() throws Exception {
        // given
        Student student = studentController.addStudent(new StudentRequest("Arfath", "Ahmed"));

        // when
        studentController.deleteStudent(student.getId());

        // then
        then(studentController.getAllStudents().iterator().hasNext()).isFalse();
    }
}
