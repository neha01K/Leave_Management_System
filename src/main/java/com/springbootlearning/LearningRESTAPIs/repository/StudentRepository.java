package com.springbootlearning.LearningRESTAPIs.repository;

import com.springbootlearning.LearningRESTAPIs.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
