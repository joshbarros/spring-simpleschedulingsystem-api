package com.goldenglowitsolutions.simpleschedulingsystem.service.impl;

import com.goldenglowitsolutions.simpleschedulingsystem.dto.CourseDTO;
import com.goldenglowitsolutions.simpleschedulingsystem.dto.StudentDTO;
import com.goldenglowitsolutions.simpleschedulingsystem.entity.Course;
import com.goldenglowitsolutions.simpleschedulingsystem.entity.Student;
import com.goldenglowitsolutions.simpleschedulingsystem.exception.EntityNotFoundException;
import com.goldenglowitsolutions.simpleschedulingsystem.repository.CourseRepository;
import com.goldenglowitsolutions.simpleschedulingsystem.repository.StudentRepository;
import com.goldenglowitsolutions.simpleschedulingsystem.service.StudentService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the StudentService interface.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Cacheable(value = "students", key = "'all'")
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public Page<StudentDTO> getPagedStudents(Pageable pageable) {
        return studentRepository.findAll(pageable)
                .map(this::convertToDTO);
    }
    
    @Override
    public List<StudentDTO> searchStudents(String query) {
        return studentRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                query, query, query).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "students", key = "#id")
    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));
        return convertToDTO(student);
    }

    @Override
    @Transactional
    @Caching(
        evict = { 
            @CacheEvict(value = "students", key = "'all'") 
        },
        put = { 
            @CachePut(value = "students", key = "#result.id") 
        }
    )
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = new Student();
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    @Override
    @Transactional
    @Caching(
        evict = { 
            @CacheEvict(value = "students", key = "'all'"),
            @CacheEvict(value = "studentCourses", key = "#id", condition = "#id != null") 
        },
        put = { 
            @CachePut(value = "students", key = "#id") 
        }
    )
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + id));
        
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        
        Student updatedStudent = studentRepository.save(student);
        return convertToDTO(updatedStudent);
    }

    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "students", key = "#id"),
        @CacheEvict(value = "students", key = "'all'"),
        @CacheEvict(value = "studentCourses", key = "#id"),
        @CacheEvict(value = "courseStudents", allEntries = true)
    })
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(value = "students", key = "#studentId"),
        @CacheEvict(value = "students", key = "'all'"),
        @CacheEvict(value = "studentCourses", key = "#studentId"),
        @CacheEvict(value = "courseStudents", allEntries = true)
    })
    public StudentDTO assignCourses(Long studentId, List<String> courseCodes) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
        
        List<Course> courses = courseRepository.findAllById(courseCodes);
        if (courses.size() != courseCodes.size()) {
            List<String> foundCodes = courses.stream().map(Course::getCode).collect(Collectors.toList());
            List<String> notFoundCodes = courseCodes.stream()
                    .filter(code -> !foundCodes.contains(code))
                    .collect(Collectors.toList());
            throw new EntityNotFoundException("Courses not found with codes: " + notFoundCodes);
        }
        
        for (Course course : courses) {
            student.addCourse(course);
        }
        
        Student updatedStudent = studentRepository.save(student);
        return convertToDTO(updatedStudent);
    }

    @Override
    @Cacheable(value = "studentCourses", key = "#studentId")
    public StudentDTO getStudentWithCourses(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
        
        StudentDTO studentDTO = convertToDTO(student);
        studentDTO.setCourses(student.getCourses().stream()
                .map(this::convertCourseToDTO)
                .collect(Collectors.toSet()));
        
        return studentDTO;
    }

    @Override
    @Cacheable(value = "courseStudents", key = "#courseCode")
    public List<StudentDTO> getStudentsByCourseCode(String courseCode) {
        if (!courseRepository.existsById(courseCode)) {
            throw new EntityNotFoundException("Course not found with code: " + courseCode);
        }
        
        return studentRepository.findByCourseCode(courseCode).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Helper method to convert a Student entity to a StudentDTO.
     *
     * @param student the Student entity
     * @return the StudentDTO
     */
    private StudentDTO convertToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setEmail(student.getEmail());
        return dto;
    }
    
    /**
     * Helper method to convert a Course entity to a CourseDTO.
     *
     * @param course the Course entity
     * @return the CourseDTO
     */
    private CourseDTO convertCourseToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setCode(course.getCode());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        return dto;
    }
} 