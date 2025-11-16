package com.example.student_management.controller;
import com.example.student_management.entity.Student;
import com.example.student_management.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@Valid @RequestBody Student student){
        try {
            Student createdStudent = studentService.createStudent(student);
            return ResponseEntity.status(201).body(createdStudent);
        } catch (RuntimeException e) {
            // Return 409 Conflict if duplicate
            return ResponseEntity.status(409).body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id){
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> getAllStudents(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    )
    {
        List<String> allowedSortFields = List.of("id", "name", "email", "course", "age");
        if (!allowedSortFields.contains(sortBy.toLowerCase())) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Invalid sortBy field: " + sortBy));
        }

        Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Student> students = studentService.getAllStudents(search, pageable);
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable String id, @Valid @RequestBody Student student){
        try {
            Student updatedStudent = studentService.updateStudent(id, student);
            return ResponseEntity.ok(updatedStudent);
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable String id){
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
