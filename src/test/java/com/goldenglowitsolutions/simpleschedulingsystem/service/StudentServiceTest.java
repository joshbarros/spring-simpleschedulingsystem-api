package com.goldenglowitsolutions.simpleschedulingsystem.service;

import com.goldenglowitsolutions.simpleschedulingsystem.dto.CourseDTO;
import com.goldenglowitsolutions.simpleschedulingsystem.dto.StudentDTO;
import com.goldenglowitsolutions.simpleschedulingsystem.entity.Course;
import com.goldenglowitsolutions.simpleschedulingsystem.entity.Student;
import com.goldenglowitsolutions.simpleschedulingsystem.exception.EntityNotFoundException;
import com.goldenglowitsolutions.simpleschedulingsystem.repository.CourseRepository;
import com.goldenglowitsolutions.simpleschedulingsystem.repository.StudentRepository;
import com.goldenglowitsolutions.simpleschedulingsystem.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student1;
    private Student student2;
    private Course course1;
    private Course course2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
        student1 = new Student();
        student1.setId(1L);
        student1.setFirstName("John");
        student1.setLastName("Doe");
        student1.setEmail("john.doe@example.com");
        student1.setCourses(new HashSet<>());

        student2 = new Student();
        student2.setId(2L);
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        student2.setEmail("jane.smith@example.com");
        student2.setCourses(new HashSet<>());

        course1 = new Course();
        course1.setCode("CS101");
        course1.setTitle("Introduction to Programming");
        course1.setDescription("Fundamental concepts of programming using Java.");
        course1.setStudents(new HashSet<>());

        course2 = new Course();
        course2.setCode("MATH101");
        course2.setTitle("Calculus I");
        course2.setDescription("Introduction to differential calculus.");
        course2.setStudents(new HashSet<>());
    }

    @Test
    void getAllStudents_ReturnsListOfStudentDTOs() {
        // Arrange
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student1, student2));

        // Act
        List<StudentDTO> students = studentService.getAllStudents();

        // Assert
        assertEquals(2, students.size());
        assertEquals("John", students.get(0).getFirstName());
        assertEquals("Jane", students.get(1).getFirstName());
    }

    @Test
    void getStudentById_WithValidId_ReturnsStudentDTO() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));

        // Act
        StudentDTO studentDTO = studentService.getStudentById(1L);

        // Assert
        assertNotNull(studentDTO);
        assertEquals(1L, studentDTO.getId());
        assertEquals("John", studentDTO.getFirstName());
    }

    @Test
    void getStudentById_WithInvalidId_ThrowsException() {
        // Arrange
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            studentService.getStudentById(999L);
        });
    }

    @Test
    void createStudent_ReturnsCreatedStudentDTO() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO(null, "New", "Student", "new.student@example.com");
        Student newStudent = new Student();
        newStudent.setId(3L);
        newStudent.setFirstName("New");
        newStudent.setLastName("Student");
        newStudent.setEmail("new.student@example.com");
        newStudent.setCourses(new HashSet<>());

        when(studentRepository.save(any(Student.class))).thenReturn(newStudent);

        // Act
        StudentDTO createdStudentDTO = studentService.createStudent(studentDTO);

        // Assert
        assertNotNull(createdStudentDTO);
        assertEquals(3L, createdStudentDTO.getId());
        assertEquals("New", createdStudentDTO.getFirstName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void updateStudent_WithValidId_ReturnsUpdatedStudentDTO() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO(1L, "Updated", "Name", "updated.email@example.com");
        Student updatedStudent = new Student();
        updatedStudent.setId(1L);
        updatedStudent.setFirstName("Updated");
        updatedStudent.setLastName("Name");
        updatedStudent.setEmail("updated.email@example.com");
        updatedStudent.setCourses(new HashSet<>());

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        // Act
        StudentDTO updatedStudentDTO = studentService.updateStudent(1L, studentDTO);

        // Assert
        assertNotNull(updatedStudentDTO);
        assertEquals("Updated", updatedStudentDTO.getFirstName());
        assertEquals("Name", updatedStudentDTO.getLastName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void deleteStudent_WithValidId_DeletesStudent() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        // Act
        studentService.deleteStudent(1L);

        // Assert
        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void getPagedStudents_ReturnsPageOfStudentDTOs() {
        // Arrange
        List<Student> students = Arrays.asList(student1, student2);
        Page<Student> studentPage = new PageImpl<>(students);
        
        when(studentRepository.findAll(any(Pageable.class))).thenReturn(studentPage);

        // Act
        Page<StudentDTO> resultPage = studentService.getPagedStudents(Pageable.unpaged());

        // Assert
        assertEquals(2, resultPage.getContent().size());
        verify(studentRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void searchStudents_ReturnsMatchingStudents() {
        // Arrange
        when(studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                anyString(), anyString(), anyString())).thenReturn(Arrays.asList(student1));

        // Act
        List<StudentDTO> results = studentService.searchStudents("John");

        // Assert
        assertEquals(1, results.size());
        assertEquals("John", results.get(0).getFirstName());
    }

    @Test
    void getStudentWithCourses_ReturnsStudentWithCourses() {
        // Arrange
        Set<Course> courses = new HashSet<>();
        courses.add(course1);
        student1.setCourses(courses);
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));

        // Act
        StudentDTO studentDTO = studentService.getStudentWithCourses(1L);

        // Assert
        assertNotNull(studentDTO);
        assertEquals(1, studentDTO.getCourses().size());
    }

    @Test
    void assignCoursesToStudent_AssignsCoursesToStudent() {
        // Arrange
        List<String> courseCodes = Arrays.asList("CS101", "MATH101");
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        when(courseRepository.findAllById(courseCodes)).thenReturn(Arrays.asList(course1, course2));
        when(studentRepository.save(any(Student.class))).thenReturn(student1);

        // Act
        StudentDTO result = studentService.assignCourses(1L, courseCodes);

        // Assert
        assertNotNull(result);
        verify(studentRepository, times(1)).save(any(Student.class));
    }
} 