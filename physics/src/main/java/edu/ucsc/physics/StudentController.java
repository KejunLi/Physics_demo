package edu.ucsc.physics;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;

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

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String requestBody, Model model) {
        System.out.println("StudentController - start logging in.");
        try {
            // Parse the JSON request body
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            System.out.println("StudentController - parsing JSON.");
            String email = jsonNode.get("email").asText();
            System.out.println("StudentController - parsed email");
            String password = jsonNode.get("password").asText();
            // Fetch user from repository
            System.out.println("login - " + email);
            Optional<Student> optionalStudent = studentService.findByEmail(email);
            Student student = optionalStudent.get();
            if (student != null && student.getPassword().equals(password)) {
                System.out.println("StudentController - " + student.getEmail());
                System.out.println("StudentController - " + student.getPassword());
                System.out.println("StudentController - " + email);
                model.addAttribute("username", email);
                System.out.println(ResponseEntity.status(HttpStatus.OK).body("Student logged in successfully!" + student));
                return ResponseEntity.status(HttpStatus.OK).body("Student logged in successfully!" + student);
            } else {
                System.out.println("StudentController - failed to logging student");
                model.addAttribute("error", "Invalid credentials");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to log in student: ");
            }
        } catch (Exception e) {
            System.out.println("StudentController - Failed to log in student!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to log in student: " + e.getMessage());
        }
    }

    // @PostMapping("/login") // RESTful endpoints which receive message from user request that will be read
    // public String login(@RequestParam String email, @RequestParam String password, Model model) {
    //     // Add your authentication logic here (e.g., check against a database)
    //     if (authenticateUser(email, password)) {
    //         model.addAttribute("username", email);
    //         return "redirect:/profile";
    //     } else {
    //         model.addAttribute("error", "Invalid credentials");
    //         return "login";
    //     }
    //     // For simplicity, let's assume any username/password is valid
    //     if ("your_username".equals(username) && "your_password".equals(password)) {
    //        model.addAttribute("username", username);
    //        return "redirect:/profile";
    //     } else {
    //        model.addAttribute("error", "Invalid username or password");
    //        return "login";
    //     }
    //}
}
