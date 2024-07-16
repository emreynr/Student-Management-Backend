package com.example.demo.repository;

import com.example.demo.entity.Grades;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grades, Long> {
}
