package com.goldenglowitsolutions.simpleschedulingsystem.service;

import com.goldenglowitsolutions.simpleschedulingsystem.dto.CourseDTO;
import java.util.List;

/**
 * Service interface for managing courses.
 */
public interface CourseService {
    
    /**
     * Get all courses.
     *
     * @return a list of all courses
     */
    List<CourseDTO> getAllCourses();
    
    /**
     * Get a course by code.
     *
     * @param code the course code
     * @return the course with the given code
     */
    CourseDTO getCourseByCode(String code);
    
    /**
     * Create a new course.
     *
     * @param courseDTO the course data
     * @return the created course
     */
    CourseDTO createCourse(CourseDTO courseDTO);
    
    /**
     * Update an existing course.
     *
     * @param code the course code
     * @param courseDTO the updated course data
     * @return the updated course
     */
    CourseDTO updateCourse(String code, CourseDTO courseDTO);
    
    /**
     * Delete a course.
     *
     * @param code the course code
     */
    void deleteCourse(String code);
    
    /**
     * Get all courses for a student.
     *
     * @param studentId the student ID
     * @return a list of courses the student is enrolled in
     */
    List<CourseDTO> getCoursesByStudentId(Long studentId);
    
    /**
     * Get courses not taken by a student.
     *
     * @param studentId the student ID
     * @return a list of courses the student is not enrolled in
     */
    List<CourseDTO> getCoursesNotTakenByStudent(Long studentId);
    
    /**
     * Get a course with its enrolled students.
     *
     * @param code the course code
     * @return the course with its students
     */
    CourseDTO getCourseWithStudents(String code);
} 