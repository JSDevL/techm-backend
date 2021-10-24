package com.hackerearth.fullstack.backend.repository;

import com.hackerearth.fullstack.backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student,Long> {
    public Student getByRoll(String roll);
    public Student findById(String id);
}
