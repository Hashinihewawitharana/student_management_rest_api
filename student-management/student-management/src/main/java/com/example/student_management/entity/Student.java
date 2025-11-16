package com.example.student_management.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Course is required")
    @Pattern(regexp = "^(ICT|EGT|BST)$", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Course must be one of: ICT, EGT, BST")
    private String course;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;
}

