package com.example.student_management.service.impl;
import com.example.student_management.entity.Student;
import com.example.student_management.exception.DuplicateEmailException;
import com.example.student_management.repository.StudentRepository;
import com.example.student_management.service.StudentService;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {

        // Check duplicate email
        if (studentRepository.existsByIdIgnoreCase(student.getId())) {
            throw new RuntimeException("Student ID already exists (case-insensitive). Cannot create duplicate.");
        }

        // Check duplicate email
        if (studentRepository.existsByEmailIgnoreCase(student.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + student.getEmail());
        }
        student.setId(student.getId().toUpperCase());
        return studentRepository.save(student);
    }



    @Override
    public Optional<Student> getStudentById(String id){
        return studentRepository.findById(id);
    }

    @Override
    public Page<Student> getAllStudents(String search, Pageable pageable) {
        if (search == null || search.isBlank()) {
            return studentRepository.findAll(pageable);
        }

        Specification<Student> spec = (root, query, criteriaBuilder) -> {
            String likeSearch = "%" + search.toLowerCase() + "%";
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), likeSearch));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("course")), likeSearch));
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };

        return studentRepository.findAll(spec, pageable);
    }

    @Override
    public Student updateStudent(String id, Student student){
        return studentRepository.findById(id).map(existingStudent -> {

            //  Check if email is changed
            boolean isEmailChanged = !existingStudent.getEmail().equalsIgnoreCase(student.getEmail());

            //  If changed, check whether that email already exists in DB
            if (isEmailChanged && studentRepository.existsByEmailIgnoreCase(student.getEmail())) {
                throw new DuplicateEmailException("Email already exists: " + student.getEmail());
            }

            existingStudent.setName(student.getName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setCourse(student.getCourse());
            existingStudent.setAge(student.getAge());
            return studentRepository.save(existingStudent);
        }).orElseThrow(() -> new RuntimeException("Student not found with id " + id));
    }

    @Override
    public void deleteStudent(String id) {
        Optional<Student> studentOpt = studentRepository.findByIdIgnoreCase(id);
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Student not found with id: " + id);
        }
        studentRepository.delete(studentOpt.get());
    }

}
