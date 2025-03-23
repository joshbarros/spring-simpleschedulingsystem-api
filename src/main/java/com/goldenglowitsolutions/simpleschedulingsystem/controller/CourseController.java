package com.goldenglowitsolutions.simpleschedulingsystem.controller;

import com.goldenglowitsolutions.simpleschedulingsystem.dto.CourseDTO;
import com.goldenglowitsolutions.simpleschedulingsystem.dto.StudentDTO;
import com.goldenglowitsolutions.simpleschedulingsystem.service.CourseService;
import com.goldenglowitsolutions.simpleschedulingsystem.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for managing courses.
 */
@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;
    private final StudentService studentService;

    public CourseController(CourseService courseService, StudentService studentService) {
        this.courseService = courseService;
        this.studentService = studentService;
    }

    /**
     * GET /courses : Get all courses
     *
     * @return the ResponseEntity with status 200 (OK) and the list of courses
     */
    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<CourseDTO> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    /**
     * GET /courses/{code} : Get a course by code
     *
     * @param code the code of the course to retrieve
     * @return the ResponseEntity with status 200 (OK) and the course, or status 404 (Not Found)
     */
    @GetMapping("/{code}")
    public ResponseEntity<CourseDTO> getCourseByCode(@PathVariable String code) {
        CourseDTO course = courseService.getCourseByCode(code);
        return ResponseEntity.ok(course);
    }

    /**
     * POST /courses : Create a new course
     *
     * @param courseDTO the course to create
     * @return the ResponseEntity with status 201 (Created) and the new course
     */
    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        CourseDTO createdCourse = courseService.createCourse(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    /**
     * PUT /courses/{code} : Update an existing course
     *
     * @param code the code of the course to update
     * @param courseDTO the course to update
     * @return the ResponseEntity with status 200 (OK) and the updated course
     */
    @PutMapping("/{code}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable String code, @Valid @RequestBody CourseDTO courseDTO) {
        CourseDTO updatedCourse = courseService.updateCourse(code, courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    /**
     * DELETE /courses/{code} : Delete a course
     *
     * @param code the code of the course to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String code) {
        courseService.deleteCourse(code);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /courses/{code}/students : Get all students enrolled in a course
     *
     * @param code the code of the course
     * @return the ResponseEntity with status 200 (OK) and the course with students
     */
    @GetMapping("/{code}/students")
    public ResponseEntity<CourseDTO> getCourseStudents(@PathVariable String code) {
        CourseDTO course = courseService.getCourseWithStudents(code);
        return ResponseEntity.ok(course);
    }

    /**
     * GET /courses/students/{studentId} : Get all courses for a student
     *
     * @param studentId the ID of the student
     * @return the ResponseEntity with status 200 (OK) and the list of courses
     */
    @GetMapping("/students/{studentId}")
    public ResponseEntity<List<CourseDTO>> getCoursesByStudentId(@PathVariable Long studentId) {
        List<CourseDTO> courses = courseService.getCoursesByStudentId(studentId);
        return ResponseEntity.ok(courses);
    }

    /**
     * GET /courses/not-taken/{studentId} : Get all courses not taken by a student
     *
     * @param studentId the ID of the student
     * @return the ResponseEntity with status 200 (OK) and the list of courses
     */
    @GetMapping("/not-taken/{studentId}")
    public ResponseEntity<List<CourseDTO>> getCoursesNotTakenByStudent(@PathVariable Long studentId) {
        List<CourseDTO> courses = courseService.getCoursesNotTakenByStudent(studentId);
        return ResponseEntity.ok(courses);
    }
} 