package com.example.demo.services;


import com.example.demo.entity.Grades;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StudentActionService {

    @Autowired
    private StudentRepository studentRepository;

    // Student "create" method
    @Transactional
    public String createStudent(Student student) {

        // firstly, it should be check our method worked smoothly or failed.. try catch blocks are good for that
        // (I think all potential exceptions should be checked for escape traditional "NullPointerException".)
        try {
            studentRepository.save(student);
            return "Student created successfully";
        } catch (Exception exception) {
            return "Student creation failed" + exception.getMessage();
        }
    }

    //student "update" method
    // update student information by given "stdNumber"
    @Transactional
    public String updateStudent(Student student) {

        try {
            Optional<Student> existingStudentOptional = studentRepository.findByStudentNumber(student.getStdNumber());

            if (existingStudentOptional.isPresent()) {
                Student existingStudent = existingStudentOptional.get();

                existingStudent.setName(student.getName());
                existingStudent.setSurname(student.getSurname());

                List<Grades> existingGrades = existingStudent.getGrades();
                for (Grades newGrade : student.getGrades()) {
                    Optional<Grades> existingGradeOptional = existingGrades.stream()
                            .filter(tempData -> tempData.getCode().equals(newGrade.getCode()))
                            .findFirst();

                    if (existingGradeOptional.isPresent()) {
                        Grades existingGrade = existingGradeOptional.get();
                        existingGrade.setValue((existingGrade.getValue() + newGrade.getValue()) / 2);
                    } else {
                        newGrade.setStudent(existingStudent);
                        existingGrades.add(newGrade);
                    }
                }

                existingStudent.setGrades(existingGrades);
                studentRepository.save(existingStudent);

                return "Student updated successfully";
            } else {
                return "Student not found";
            }
        } catch (Exception exception) {
            return "Student update failed" + exception.getMessage();
        }
    }

    // findByStudentNumber method
    public Optional<Student> findStudentByStudentNumber(String studentNumber) {
        return studentRepository.findByStudentNumber(studentNumber);
    }
}