package com.goldenglowitsolutions.simpleschedulingsystem.repository;

import com.goldenglowitsolutions.simpleschedulingsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for Student entity providing CRUD operations and custom queries.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    /**
     * Find students by course code.
     *
     * @param courseCode the course code
     * @return a list of students enrolled in the course
     */
    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.code = :courseCode")
    List<Student> findByCourseCode(@Param("courseCode") String courseCode);
    
    /**
     * Find students by first name, last name, or email containing the search term.
     *
     * @param searchTerm the search term
     * @return a list of students matching the search term
     */
    List<Student> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String searchTerm, String searchTerm1, String searchTerm2);
} 