package com.goldenglowitsolutions.simpleschedulingsystem.controller;

import com.goldenglowitsolutions.simpleschedulingsystem.dto.StudentDTO;
import com.goldenglowitsolutions.simpleschedulingsystem.service.StudentService;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for managing students.
 */
@RestController
@RequestMapping("/students")
@Timed(value = "students", description = "Time taken to execute student controller operations")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * GET /students : Get all students
     *
     * @return the ResponseEntity with status 200 (OK) and the list of students
     */
    @GetMapping
    @Timed(value = "students.getAll", description = "Time taken to get all students")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    /**
     * GET /students/paged : Get all students with pagination
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the paged list of students
     */
    @GetMapping("/paged")
    @Timed(value = "students.getPaged", description = "Time taken to get paged students")
    public ResponseEntity<Page<StudentDTO>> getPagedStudents(
            @PageableDefault(size = 10, sort = "lastName") Pageable pageable) {
        Page<StudentDTO> students = studentService.getPagedStudents(pageable);
        return ResponseEntity.ok(students);
    }
    
    /**
     * GET /students/search : Search for students
     *
     * @param query the search query
     * @return the ResponseEntity with status 200 (OK) and the list of matching students
     */
    @GetMapping("/search")
    @Timed(value = "students.search", description = "Time taken to search students")
    public ResponseEntity<List<StudentDTO>> searchStudents(@RequestParam String query) {
        List<StudentDTO> students = studentService.searchStudents(query);
        return ResponseEntity.ok(students);
    }

    /**
     * GET /students/{id} : Get a student by ID
     *
     * @param id the ID of the student to retrieve
     * @return the ResponseEntity with status 200 (OK) and the student, or status 404 (Not Found)
     */
    @GetMapping("/{id}")
    @Timed(value = "students.getById", description = "Time taken to get student by ID")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    /**
     * POST /students : Create a new student
     *
     * @param studentDTO the student to create
     * @return the ResponseEntity with status 201 (Created) and the new student
     */
    @PostMapping
    @Timed(value = "students.create", description = "Time taken to create a student")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    /**
     * PUT /students/{id} : Update an existing student
     *
     * @param id the ID of the student to update
     * @param studentDTO the student to update
     * @return the ResponseEntity with status 200 (OK) and the updated student
     */
    @PutMapping("/{id}")
    @Timed(value = "students.update", description = "Time taken to update a student")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updatedStudent);
    }

    /**
     * DELETE /students/{id} : Delete a student
     *
     * @param id the ID of the student to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    @Timed(value = "students.delete", description = "Time taken to delete a student")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /students/{id}/courses : Get all courses for a student
     *
     * @param id the ID of the student
     * @return the ResponseEntity with status 200 (OK) and the student with courses
     */
    @GetMapping("/{id}/courses")
    @Timed(value = "students.getCourses", description = "Time taken to get student courses")
    public ResponseEntity<StudentDTO> getStudentCourses(@PathVariable Long id) {
        StudentDTO student = studentService.getStudentWithCourses(id);
        return ResponseEntity.ok(student);
    }

    /**
     * POST /students/{id}/courses : Assign courses to a student
     *
     * @param id the ID of the student
     * @param courseCodes the list of course codes to assign
     * @return the ResponseEntity with status 200 (OK) and the updated student
     */
    @PostMapping("/{id}/courses")
    @Timed(value = "students.assignCourses", description = "Time taken to assign courses to a student")
    public ResponseEntity<StudentDTO> assignCourses(@PathVariable Long id, @RequestBody List<String> courseCodes) {
        StudentDTO updatedStudent = studentService.assignCourses(id, courseCodes);
        return ResponseEntity.ok(updatedStudent);
    }
} 