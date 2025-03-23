package com.goldenglowitsolutions.simpleschedulingsystem.service.impl;

import com.goldenglowitsolutions.simpleschedulingsystem.dto.CourseDTO;
import com.goldenglowitsolutions.simpleschedulingsystem.dto.StudentDTO;
import com.goldenglowitsolutions.simpleschedulingsystem.entity.Course;
import com.goldenglowitsolutions.simpleschedulingsystem.entity.Student;
import com.goldenglowitsolutions.simpleschedulingsystem.exception.EntityNotFoundException;
import com.goldenglowitsolutions.simpleschedulingsystem.repository.CourseRepository;
import com.goldenglowitsolutions.simpleschedulingsystem.repository.StudentRepository;
import com.goldenglowitsolutions.simpleschedulingsystem.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the CourseService interface.
 */
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO getCourseByCode(String code) {
        Course course = courseRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with code: " + code));
        return convertToDTO(course);
    }

    @Override
    @Transactional
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = new Course();
        course.setCode(courseDTO.getCode());
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());

        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    @Override
    @Transactional
    public CourseDTO updateCourse(String code, CourseDTO courseDTO) {
        Course course = courseRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with code: " + code));

        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());

        Course updatedCourse = courseRepository.save(course);
        return convertToDTO(updatedCourse);
    }

    @Override
    @Transactional
    public void deleteCourse(String code) {
        if (!courseRepository.existsById(code)) {
            throw new EntityNotFoundException("Course not found with code: " + code);
        }
        courseRepository.deleteById(code);
    }

    @Override
    public List<CourseDTO> getCoursesByStudentId(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new EntityNotFoundException("Student not found with ID: " + studentId);
        }

        return courseRepository.findByStudentId(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseDTO> getCoursesNotTakenByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new EntityNotFoundException("Student not found with ID: " + studentId);
        }

        return courseRepository.findCoursesNotTakenByStudent(studentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO getCourseWithStudents(String code) {
        Course course = courseRepository.findById(code)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with code: " + code));

        CourseDTO courseDTO = convertToDTO(course);
        courseDTO.setStudents(course.getStudents().stream()
                .map(this::convertStudentToDTO)
                .collect(Collectors.toSet()));

        return courseDTO;
    }

    /**
     * Helper method to convert a Course entity to a CourseDTO.
     *
     * @param course the Course entity
     * @return the CourseDTO
     */
    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCode(course.getCode());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        return dto;
    }

    /**
     * Helper method to convert a Student entity to a StudentDTO.
     *
     * @param student the Student entity
     * @return the StudentDTO
     */
    private StudentDTO convertStudentToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        return dto;
    }
} 