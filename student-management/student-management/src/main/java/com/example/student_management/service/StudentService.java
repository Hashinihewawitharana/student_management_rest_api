package com.example.student_management.service;
import com.example.student_management.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StudentService {
    Student createStudent(Student student);
    Optional<Student> getStudentById(String id);
    Page<Student> getAllStudents(String search, Pageable pageable);
    Student updateStudent(String id, Student student);
    void deleteStudent(String id);
}


