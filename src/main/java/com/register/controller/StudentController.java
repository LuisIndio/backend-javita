package com.register.controller;

import com.register.model.Student;
import com.register.repository.StudentRepository;
import com.register.storage.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Student savedStudent = studentRepository.save(student);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    @GetMapping("/list/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @PostMapping("/students/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        Student student = studentRepository.findById(id).orElse(null);

        if (student == null) {
            response.put("message", "Student not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        if (!file.isEmpty()) {
            String fileName = null;
            try {
                fileName = storageService.store(file);
            } catch (Exception e) {
                response.put("message", "Error to upload image");
                response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            String namePhotoBeforeDelete = student.getImagePath();
            if (namePhotoBeforeDelete != null) {
                storageService.delete(namePhotoBeforeDelete);
            }

            student.setImagePath(fileName);
            Student savedStudent = studentRepository.save(student);

            // Recuperar el estudiante de la base de datos para verificar si la imagen se asignó correctamente
            Student verifyStudent = studentRepository.findById(id).orElse(null);
            if (verifyStudent != null) {
                String imagePath = verifyStudent.getImagePath();
                if (imagePath != null && imagePath.equals(fileName)) {
                    System.out.println("La imagen se asignó correctamente al estudiante");
                } else {
                    System.out.println("La imagen no se asignó correctamente al estudiante");
                }
            } else {
                System.out.println("El estudiante no se encontró en la base de datos");
            }

            response.put("message", "Image uploaded successfully");
            response.put("student", savedStudent);
        }

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}