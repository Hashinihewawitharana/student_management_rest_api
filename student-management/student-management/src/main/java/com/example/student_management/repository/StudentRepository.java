package com.example.student_management.repository;

import com.example.student_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String>, JpaSpecificationExecutor<Student> {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Student s WHERE LOWER(s.id) = LOWER(:id)")
    boolean existsByIdIgnoreCase(@Param("id") String id);

    @Query("SELECT s FROM Student s WHERE LOWER(s.id) = LOWER(:id)")
    Optional<Student> findByIdIgnoreCase(@Param("id") String id);

    //  to add delete by id ignore case
    @Query("DELETE FROM Student s WHERE LOWER(s.id) = LOWER(:id)")
    void deleteByIdIgnoreCase(@Param("id") String id);

    // prevent duplicate email add
    boolean existsByEmailIgnoreCase(String email);

}



