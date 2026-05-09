package com.example.Estudiante;

import com.example.Estudiante.Student;
import com.example.Estudiante.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    public Student createStudent(Student student) {
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student student) {
        Student existing = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        if (!existing.getEmail().equals(student.getEmail()) && studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        existing.setName(student.getName());
        existing.setEmail(student.getEmail());
        existing.setCareer(student.getCareer());
        existing.setAge(student.getAge());
        return studentRepository.save(existing);
    }

    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Not found");
        }
        studentRepository.deleteById(id);
    }
}
