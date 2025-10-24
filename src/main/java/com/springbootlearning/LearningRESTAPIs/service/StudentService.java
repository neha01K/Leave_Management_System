package com.springbootlearning.LearningRESTAPIs.service;

import com.springbootlearning.LearningRESTAPIs.dto.AddStudentRequestDto;
import com.springbootlearning.LearningRESTAPIs.dto.StudentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface StudentService {

    List<StudentDto> getAllStudents();
    StudentDto getStudentById(Long id);

    StudentDto createNewStudent(AddStudentRequestDto addStudentRequestDto);

    void deleteStudentById(Long id);

    StudentDto updateStudent(Long id, AddStudentRequestDto addStudentRequestDto);
    StudentDto updatePartialStudent(Long id, Map<String, Object> updates);
}
