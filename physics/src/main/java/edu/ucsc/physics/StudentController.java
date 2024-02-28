package edu.ucsc.physics;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class StudentController {

    private final StudentService studentService;
    private final ObjectMapper objectMapper;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
        this.objectMapper = new ObjectMapper();
    }
    @PostMapping(value = "/register")
    public ResponseEntity<String> registerStudent(@RequestBody String requestBody) {
        System.out.println("StudentController - preparing to register a new student...");
        System.out.println("StudentController - requestBody is " + requestBody);
        Student student = new Student();
        try {
            // Parse the JSON request body
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            String email = jsonNode.get("email").asText();
            String password = jsonNode.get("password").asText();
            String studentId = jsonNode.get("studentId").asText();
            student.setEmail(email);
            student.setPassword(password);
            student.setStudentId(studentId);
            studentService.registerStudent(student);
            System.out.println("StudentController - Student registered successfully!");
            return ResponseEntity.status(HttpStatus.OK).body("Student registered successfully!" + student);
        } catch (Exception e) {
            System.out.println("StudentController - Failed to register student!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register student: " + e.getMessage());
        }
    }
}
