package edu.ucsc.physics;
// StudentService.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Optional<Student> existingStudentOptional = studentRepository.findById(id);

        if (existingStudentOptional.isPresent()) {
            Student existingStudent = existingStudentOptional.get();
            existingStudent.setStudentId(updatedStudent.getStudentId());
            existingStudent.setEmail(updatedStudent.getEmail());
            existingStudent.setName(updatedStudent.getName());

            return studentRepository.save(existingStudent);
        } else {
            throw new IllegalArgumentException("Student not found with id: " + id);
        }
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
