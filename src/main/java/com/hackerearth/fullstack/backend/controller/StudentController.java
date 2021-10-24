package com.hackerearth.fullstack.backend.controller;

import com.hackerearth.fullstack.backend.exception.CustomException;
import com.hackerearth.fullstack.backend.model.Student;
import com.hackerearth.fullstack.backend.payload.request.StudentRequest;
import com.hackerearth.fullstack.backend.payload.response.MessageResponse;
import com.hackerearth.fullstack.backend.repository.StudentRepository;
import com.hackerearth.fullstack.backend.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:5000")
@RestController
@RequestMapping("/api/v1")
public class StudentController {
    final private StudentRepository studentRepository;

    @PostMapping("/students")
    public Student addStudent(@RequestBody StudentRequest studentRequest) throws CustomException {

        /*
        Complete this function to get a post request as a student taking
        the parameters as Student Name and Student Roll Number
        */
        if (studentRequest.getName() == null || studentRequest.getName().length() == 0) {
            throw new CustomException(Constants.INVALID_NAME_MESSAGE, Constants.INVALID_NAME);
        }

        if (studentRequest.getRoll() == null || studentRequest.getRoll().length() == 0) {
            throw new CustomException(Constants.INVALID_ROLL_MESSAGE, Constants.INVALID_ROLL);
        }

        Student student = studentRepository.getByRoll(studentRequest.getRoll());

        if (student != null) {
            throw new CustomException(Constants.STUDENT_ALREADY_PRESENT_MESSAGE, Constants.STUDENT_ALREADY_PRESENT);
        }

        student = new Student(studentRequest.getName(), studentRequest.getRoll());

        studentRepository.save(student);

        return student;
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<MessageResponse> deleteStudent(@PathVariable long id) throws CustomException {

        /*
        Complete this function to delete as a student taking
        the parameters as student id
        */
        Optional<Student> student = studentRepository.findById(id);
        if (!student.isPresent()) {
            throw new CustomException(Constants.NO_SUCH_STUDENT_MESSAGE, Constants.NO_SUCH_STUDENT);
        }
        studentRepository.delete(student.get());

        return ResponseEntity.ok(new MessageResponse("deleted"));
    }

    @GetMapping("/students")
    public Iterable<Student> getAllStudents() {
        /*
        Complete this function to get all the students as get request
        the parameters as student id
        */
        //
        return studentRepository.findAll();
    }
}
