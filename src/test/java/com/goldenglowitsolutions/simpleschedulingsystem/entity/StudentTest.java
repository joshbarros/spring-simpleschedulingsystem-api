package com.goldenglowitsolutions.simpleschedulingsystem.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    @Test
    void testStudentCreation() {
        // Arrange
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");

        // Assert
        assertEquals(1L, student.getId());
        assertEquals("John", student.getFirstName());
        assertEquals("Doe", student.getLastName());
        assertEquals("john.doe@example.com", student.getEmail());
    }

    @Test
    void testEquality() {
        // Arrange
        Student student1 = new Student();
        student1.setId(1L);
        student1.setFirstName("John");
        student1.setLastName("Doe");
        student1.setEmail("john.doe@example.com");

        Student student2 = new Student();
        student2.setId(1L);
        student2.setFirstName("John");
        student2.setLastName("Doe");
        student2.setEmail("john.doe@example.com");

        Student student3 = new Student();
        student3.setId(2L);
        student3.setFirstName("Jane");
        student3.setLastName("Smith");
        student3.setEmail("jane.smith@example.com");

        // Assert
        assertEquals(student1, student2, "Students with same ID should be equal");
        assertNotEquals(student1, student3, "Students with different IDs should not be equal");
    }
} 