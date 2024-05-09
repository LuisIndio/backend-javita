package com.register.controller;

import com.register.model.Student;
import com.register.repository.StudentRepository;
import com.register.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/student")
public class StudentController {
    private final StorageService storageService;

    @Autowired
    private StudentRepository studentRepository;

    public StudentController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/list")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/list/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createStudent(@RequestPart("student") Student student, @RequestPart("file") MultipartFile file) {
        if (student == null) {
            return new ResponseEntity<>("Student object is null", HttpStatus.BAD_REQUEST);
        }
        if (file.isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }
        if (student.getStudentName() == null || student.getStudentName().isEmpty()) {
            return new ResponseEntity<>("Student name is required", HttpStatus.BAD_REQUEST);
        }
        // Add more field checks as necessary

        String imagePath = storageService.store(file);
        student.setImagePath(imagePath);
        Student savedStudent = studentRepository.save(student);
        return ResponseEntity.ok(savedStudent);
    }

}