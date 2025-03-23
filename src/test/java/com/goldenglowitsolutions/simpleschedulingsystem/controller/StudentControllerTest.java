package com.goldenglowitsolutions.simpleschedulingsystem.controller;

import com.goldenglowitsolutions.simpleschedulingsystem.dto.StudentDTO;
import com.goldenglowitsolutions.simpleschedulingsystem.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllStudents_ReturnsListOfStudents() {
        // Arrange
        StudentDTO student1 = new StudentDTO(1L, "John", "Doe", "john.doe@example.com");
        StudentDTO student2 = new StudentDTO(2L, "Jane", "Smith", "jane.smith@example.com");
        List<StudentDTO> students = Arrays.asList(student1, student2);
        
        when(studentService.getAllStudents()).thenReturn(students);

        // Act
        ResponseEntity<List<StudentDTO>> response = studentController.getAllStudents();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void getStudentById_WithValidId_ReturnsStudent() {
        // Arrange
        Long studentId = 1L;
        StudentDTO student = new StudentDTO(studentId, "John", "Doe", "john.doe@example.com");
        
        when(studentService.getStudentById(studentId)).thenReturn(student);

        // Act
        ResponseEntity<StudentDTO> response = studentController.getStudentById(studentId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(studentId, response.getBody().getId());
        verify(studentService, times(1)).getStudentById(studentId);
    }

    @Test
    void createStudent_WithValidData_ReturnsCreatedStudent() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO(null, "John", "Doe", "john.doe@example.com");
        StudentDTO createdStudent = new StudentDTO(1L, "John", "Doe", "john.doe@example.com");
        
        when(studentService.createStudent(studentDTO)).thenReturn(createdStudent);

        // Act
        ResponseEntity<StudentDTO> response = studentController.createStudent(studentDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(studentService, times(1)).createStudent(studentDTO);
    }
} 