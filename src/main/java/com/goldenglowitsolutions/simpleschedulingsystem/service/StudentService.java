package com.goldenglowitsolutions.simpleschedulingsystem.service;

import com.goldenglowitsolutions.simpleschedulingsystem.dto.StudentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing students.
 */
public interface StudentService {
    
    /**
     * Get all students.
     *
     * @return a list of all students
     */
    List<StudentDTO> getAllStudents();
    
    /**
     * Get a page of students.
     *
     * @param pageable the pagination information
     * @return a page of students
     */
    Page<StudentDTO> getPagedStudents(Pageable pageable);
    
    /**
     * Search for students by query.
     *
     * @param query the search query
     * @return a list of students matching the query
     */
    List<StudentDTO> searchStudents(String query);
    
    /**
     * Get a student by ID.
     *
     * @param id the student ID
     * @return the student with the given ID
     */
    StudentDTO getStudentById(Long id);
    
    /**
     * Create a new student.
     *
     * @param studentDTO the student data
     * @return the created student
     */
    StudentDTO createStudent(StudentDTO studentDTO);
    
    /**
     * Update an existing student.
     *
     * @param id the student ID
     * @param studentDTO the updated student data
     * @return the updated student
     */
    StudentDTO updateStudent(Long id, StudentDTO studentDTO);
    
    /**
     * Delete a student.
     *
     * @param id the student ID
     */
    void deleteStudent(Long id);
    
    /**
     * Assign courses to a student.
     *
     * @param studentId the student ID
     * @param courseCodes the course codes to assign
     * @return the student with the assigned courses
     */
    StudentDTO assignCourses(Long studentId, List<String> courseCodes);
    
    /**
     * Get all courses for a student.
     *
     * @param studentId the student ID
     * @return the student with their courses
     */
    StudentDTO getStudentWithCourses(Long studentId);
    
    /**
     * Get students by course code.
     *
     * @param courseCode the course code
     * @return a list of students enrolled in the course
     */
    List<StudentDTO> getStudentsByCourseCode(String courseCode);
} 