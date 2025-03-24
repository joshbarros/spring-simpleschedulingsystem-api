package com.goldenglowitsolutions.simpleschedulingsystem.service;

import com.goldenglowitsolutions.simpleschedulingsystem.dto.CourseDTO;
import com.goldenglowitsolutions.simpleschedulingsystem.entity.Course;
import com.goldenglowitsolutions.simpleschedulingsystem.entity.Student;
import com.goldenglowitsolutions.simpleschedulingsystem.exception.EntityNotFoundException;
import com.goldenglowitsolutions.simpleschedulingsystem.repository.CourseRepository;
import com.goldenglowitsolutions.simpleschedulingsystem.repository.StudentRepository;
import com.goldenglowitsolutions.simpleschedulingsystem.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course1;
    private Course course2;
    private Student student1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up test data
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

        student1 = new Student();
        student1.setId(1L);
        student1.setFirstName("John");
        student1.setLastName("Doe");
        student1.setEmail("john.doe@example.com");
        student1.setCourses(new HashSet<>());
    }

    @Test
    void getAllCourses_ReturnsListOfCourseDTOs() {
        // Arrange
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        // Act
        List<CourseDTO> courses = courseService.getAllCourses();

        // Assert
        assertEquals(2, courses.size());
        assertEquals("CS101", courses.get(0).getCode());
        assertEquals("MATH101", courses.get(1).getCode());
    }

    @Test
    void getCourseByCode_WithValidCode_ReturnsCourseDTO() {
        // Arrange
        when(courseRepository.findById("CS101")).thenReturn(Optional.of(course1));

        // Act
        CourseDTO courseDTO = courseService.getCourseByCode("CS101");

        // Assert
        assertNotNull(courseDTO);
        assertEquals("CS101", courseDTO.getCode());
        assertEquals("Introduction to Programming", courseDTO.getTitle());
    }

    @Test
    void getCourseByCode_WithInvalidCode_ThrowsException() {
        // Arrange
        when(courseRepository.findById("INVALID")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            courseService.getCourseByCode("INVALID");
        });
    }

    @Test
    void createCourse_ReturnsCreatedCourseDTO() {
        // Arrange
        CourseDTO courseDTO = new CourseDTO("NEW101", "New Course", "Description for new course");
        Course newCourse = new Course();
        newCourse.setCode("NEW101");
        newCourse.setTitle("New Course");
        newCourse.setDescription("Description for new course");
        newCourse.setStudents(new HashSet<>());

        when(courseRepository.save(any(Course.class))).thenReturn(newCourse);

        // Act
        CourseDTO createdCourseDTO = courseService.createCourse(courseDTO);

        // Assert
        assertNotNull(createdCourseDTO);
        assertEquals("NEW101", createdCourseDTO.getCode());
        assertEquals("New Course", createdCourseDTO.getTitle());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void updateCourse_WithValidCode_ReturnsUpdatedCourseDTO() {
        // Arrange
        CourseDTO courseDTO = new CourseDTO("CS101", "Updated Programming Course", "Updated description");
        Course updatedCourse = new Course();
        updatedCourse.setCode("CS101");
        updatedCourse.setTitle("Updated Programming Course");
        updatedCourse.setDescription("Updated description");
        updatedCourse.setStudents(new HashSet<>());

        when(courseRepository.findById("CS101")).thenReturn(Optional.of(course1));
        when(courseRepository.save(any(Course.class))).thenReturn(updatedCourse);

        // Act
        CourseDTO updatedCourseDTO = courseService.updateCourse("CS101", courseDTO);

        // Assert
        assertNotNull(updatedCourseDTO);
        assertEquals("Updated Programming Course", updatedCourseDTO.getTitle());
        assertEquals("Updated description", updatedCourseDTO.getDescription());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void deleteCourse_WithValidCode_DeletesCourse() {
        // Arrange
        when(courseRepository.findById("CS101")).thenReturn(Optional.of(course1));
        when(courseRepository.existsById("CS101")).thenReturn(true);
        doNothing().when(courseRepository).deleteById("CS101");

        // Act
        courseService.deleteCourse("CS101");

        // Assert
        verify(courseRepository, times(1)).deleteById("CS101");
    }

    @Test
    void getCourseWithStudents_ReturnsCourseWithStudents() {
        // Arrange
        Set<Student> students = new HashSet<>();
        students.add(student1);
        course1.setStudents(students);
        
        when(courseRepository.findById("CS101")).thenReturn(Optional.of(course1));

        // Act
        CourseDTO courseDTO = courseService.getCourseWithStudents("CS101");

        // Assert
        assertNotNull(courseDTO);
        assertEquals(1, courseDTO.getStudents().size());
    }

    @Test
    void getCoursesByStudentId_ReturnsListOfCourses() {
        // Arrange
        Set<Course> courses = new HashSet<>();
        courses.add(course1);
        courses.add(course2);
        student1.setCourses(courses);
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        when(studentRepository.existsById(1L)).thenReturn(true);
        when(courseRepository.findByStudentId(1L)).thenReturn(Arrays.asList(course1, course2));

        // Act
        List<CourseDTO> courseDTOs = courseService.getCoursesByStudentId(1L);

        // Assert
        assertEquals(2, courseDTOs.size());
    }

    @Test
    void getCoursesNotTakenByStudent_ReturnsListOfCourses() {
        // Arrange
        Set<Course> studentCourses = new HashSet<>();
        studentCourses.add(course1);
        student1.setCourses(studentCourses);
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        when(studentRepository.existsById(1L)).thenReturn(true);
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));
        when(courseRepository.findCoursesNotTakenByStudent(1L)).thenReturn(Arrays.asList(course2));

        // Act
        List<CourseDTO> courseDTOs = courseService.getCoursesNotTakenByStudent(1L);

        // Assert
        assertEquals(1, courseDTOs.size());
        assertEquals("MATH101", courseDTOs.get(0).getCode());
    }
} 