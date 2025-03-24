package com.goldenglowitsolutions.simpleschedulingsystem.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

class CourseTest {

    @Test
    void testCourseCreation() {
        // Arrange
        Course course = new Course();
        course.setCode("CS101");
        course.setTitle("Introduction to Programming");
        course.setDescription("Fundamental concepts of programming using Java.");
        course.setStudents(new HashSet<>());

        // Assert
        assertEquals("CS101", course.getCode());
        assertEquals("Introduction to Programming", course.getTitle());
        assertEquals("Fundamental concepts of programming using Java.", course.getDescription());
        assertTrue(course.getStudents().isEmpty());
    }

    @Test
    void testEquality() {
        // Arrange
        Course course1 = new Course();
        course1.setCode("CS101");
        course1.setTitle("Introduction to Programming");
        course1.setDescription("Description 1");

        Course course2 = new Course();
        course2.setCode("CS101");
        course2.setTitle("Intro to Programming");  // Different title
        course2.setDescription("Description 2");   // Different description

        Course course3 = new Course();
        course3.setCode("MATH101");
        course3.setTitle("Introduction to Programming");
        course3.setDescription("Description 1");

        // Assert
        assertEquals(course1, course2, "Courses with same code should be equal");
        assertNotEquals(course1, course3, "Courses with different codes should not be equal");
    }

    @Test
    void testHashCode() {
        // Arrange
        Course course1 = new Course();
        course1.setCode("CS101");
        course1.setTitle("Introduction to Programming");

        Course course2 = new Course();
        course2.setCode("CS101");
        course2.setTitle("Different Title");  // Different title shouldn't affect hashCode

        // Assert
        assertEquals(course1.hashCode(), course2.hashCode(), "Courses with same code should have same hashCode");
    }

    @Test
    void testToString() {
        // Arrange
        Course course = new Course();
        course.setCode("CS101");
        course.setTitle("Introduction to Programming");
        course.setDescription("Fundamental concepts of programming using Java.");

        // Act
        String courseString = course.toString();

        // Assert
        assertTrue(courseString.contains("CS101"), "toString should contain course code");
        assertTrue(courseString.contains("Introduction to Programming"), "toString should contain course title");
    }

    @Test
    void testStudentAssociation() {
        // Arrange
        Course course = new Course();
        course.setCode("CS101");
        course.setTitle("Introduction to Programming");
        
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");
        
        HashSet<Student> students = new HashSet<>();
        students.add(student);
        course.setStudents(students);

        // Assert
        assertEquals(1, course.getStudents().size());
        assertTrue(course.getStudents().contains(student));
    }
} 