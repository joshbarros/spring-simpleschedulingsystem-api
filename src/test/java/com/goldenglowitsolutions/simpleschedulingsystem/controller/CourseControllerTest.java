package com.goldenglowitsolutions.simpleschedulingsystem.controller;

import com.goldenglowitsolutions.simpleschedulingsystem.dto.CourseDTO;
import com.goldenglowitsolutions.simpleschedulingsystem.dto.StudentDTO;
import com.goldenglowitsolutions.simpleschedulingsystem.service.CourseService;
import com.goldenglowitsolutions.simpleschedulingsystem.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCourses_ReturnsListOfCourses() {
        // Arrange
        CourseDTO course1 = new CourseDTO("CS101", "Introduction to Programming", "Description 1");
        CourseDTO course2 = new CourseDTO("MATH101", "Calculus I", "Description 2");
        List<CourseDTO> courses = Arrays.asList(course1, course2);
        
        when(courseService.getAllCourses()).thenReturn(courses);

        // Act
        ResponseEntity<List<CourseDTO>> response = courseController.getAllCourses();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(courseService, times(1)).getAllCourses();
    }

    @Test
    void getCourseByCode_WithValidCode_ReturnsCourse() {
        // Arrange
        String courseCode = "CS101";
        CourseDTO course = new CourseDTO(courseCode, "Introduction to Programming", "Description 1");
        
        when(courseService.getCourseByCode(courseCode)).thenReturn(course);

        // Act
        ResponseEntity<CourseDTO> response = courseController.getCourseByCode(courseCode);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(courseCode, response.getBody().getCode());
        verify(courseService, times(1)).getCourseByCode(courseCode);
    }

    @Test
    void createCourse_WithValidData_ReturnsCreatedCourse() {
        // Arrange
        CourseDTO courseDTO = new CourseDTO("CS101", "Introduction to Programming", "Description 1");
        
        when(courseService.createCourse(courseDTO)).thenReturn(courseDTO);

        // Act
        ResponseEntity<CourseDTO> response = courseController.createCourse(courseDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("CS101", response.getBody().getCode());
        verify(courseService, times(1)).createCourse(courseDTO);
    }

    @Test
    void updateCourse_WithValidData_ReturnsUpdatedCourse() {
        // Arrange
        String courseCode = "CS101";
        CourseDTO courseDTO = new CourseDTO(courseCode, "Updated Programming Course", "Updated Description");
        
        when(courseService.updateCourse(courseCode, courseDTO)).thenReturn(courseDTO);

        // Act
        ResponseEntity<CourseDTO> response = courseController.updateCourse(courseCode, courseDTO);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Programming Course", response.getBody().getTitle());
        verify(courseService, times(1)).updateCourse(courseCode, courseDTO);
    }

    @Test
    void deleteCourse_WithValidCode_ReturnsNoContent() {
        // Arrange
        String courseCode = "CS101";
        doNothing().when(courseService).deleteCourse(courseCode);

        // Act
        ResponseEntity<Void> response = courseController.deleteCourse(courseCode);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(courseService, times(1)).deleteCourse(courseCode);
    }

    @Test
    void getCourseStudents_WithValidCode_ReturnsCourseWithStudents() {
        // Arrange
        String courseCode = "CS101";
        StudentDTO student = new StudentDTO(1L, "John", "Doe", "john.doe@example.com");
        CourseDTO course = new CourseDTO(courseCode, "Introduction to Programming", "Description");
        course.setStudents(Collections.singleton(student));
        
        when(courseService.getCourseWithStudents(courseCode)).thenReturn(course);

        // Act
        ResponseEntity<CourseDTO> response = courseController.getCourseStudents(courseCode);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getStudents().size());
        verify(courseService, times(1)).getCourseWithStudents(courseCode);
    }

    @Test
    void getCoursesByStudentId_WithValidId_ReturnsListOfCourses() {
        // Arrange
        Long studentId = 1L;
        CourseDTO course1 = new CourseDTO("CS101", "Introduction to Programming", "Description 1");
        CourseDTO course2 = new CourseDTO("MATH101", "Calculus I", "Description 2");
        List<CourseDTO> courses = Arrays.asList(course1, course2);
        
        when(courseService.getCoursesByStudentId(studentId)).thenReturn(courses);

        // Act
        ResponseEntity<List<CourseDTO>> response = courseController.getCoursesByStudentId(studentId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(courseService, times(1)).getCoursesByStudentId(studentId);
    }

    @Test
    void getCoursesNotTakenByStudent_WithValidId_ReturnsListOfCourses() {
        // Arrange
        Long studentId = 1L;
        CourseDTO course1 = new CourseDTO("CS201", "Data Structures", "Description 1");
        CourseDTO course2 = new CourseDTO("MATH201", "Linear Algebra", "Description 2");
        List<CourseDTO> courses = Arrays.asList(course1, course2);
        
        when(courseService.getCoursesNotTakenByStudent(studentId)).thenReturn(courses);

        // Act
        ResponseEntity<List<CourseDTO>> response = courseController.getCoursesNotTakenByStudent(studentId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(courseService, times(1)).getCoursesNotTakenByStudent(studentId);
    }
} 