package com.goldenglowitsolutions.simpleschedulingsystem.repository;

import com.goldenglowitsolutions.simpleschedulingsystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for Course entity providing CRUD operations and custom queries.
 */
public interface CourseRepository extends JpaRepository<Course, String> {
    
    /**
     * Find courses by student ID.
     *
     * @param studentId the student ID
     * @return a list of courses the student is enrolled in
     */
    @Query("SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId")
    List<Course> findByStudentId(@Param("studentId") Long studentId);
    
    /**
     * Find courses not taken by a student.
     *
     * @param studentId the student ID
     * @return a list of courses the student is not enrolled in
     */
    @Query("SELECT c FROM Course c WHERE c NOT IN (SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId)")
    List<Course> findCoursesNotTakenByStudent(@Param("studentId") Long studentId);
    
    /**
     * Find courses by title or description containing the search term.
     *
     * @param searchTerm the search term
     * @return a list of courses matching the search term
     */
    List<Course> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String searchTerm, String searchTerm1);
} 