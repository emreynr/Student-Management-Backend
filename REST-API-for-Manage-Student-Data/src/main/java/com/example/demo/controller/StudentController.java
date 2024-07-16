package com.example.demo.controller;

import com.example.demo.dto.GradeDTO;
import com.example.demo.dto.StudentDTO;
import com.example.demo.entity.Grades;
import com.example.demo.entity.Student;
import com.example.demo.services.StudentActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/services")
public class StudentController {

    @Autowired
    private StudentActionService studentActionService;

    // "create" endpoint
    @PostMapping("/create")
    public String create(@RequestBody StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());
        student.setStdNumber(studentDTO.getStdNumber());

        List<Grades> allGrades = new ArrayList<>();
        for(GradeDTO gradeDTO : studentDTO.getGrades()) {
            Grades grades = new Grades();
            grades.setCode(gradeDTO.getCode());
            grades.setValue(gradeDTO.getValue());

            grades.setStudent(student);
            allGrades.add(grades);
        }
        student.setGrades(allGrades);

        return studentActionService.createStudent(student);
    }

    // "update" endpoint"
    @PutMapping("/update/{stdNumber}")
    public String update(@PathVariable String stdNumber, @RequestBody StudentDTO studentDTO) {
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setSurname(studentDTO.getSurname());
        student.setStdNumber(stdNumber);

        List<Grades> allGrades = new ArrayList<>();
        for(GradeDTO gradeDTO : studentDTO.getGrades()) {
            Grades grades = new Grades();
            grades.setCode(gradeDTO.getCode());
            grades.setValue(gradeDTO.getValue());

            grades.setStudent(student);
            allGrades.add(grades);
        }

        student.setGrades(allGrades);

        return studentActionService.updateStudent(student);
    }
}