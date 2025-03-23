package com.goldenglowitsolutions.simpleschedulingsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Data Transfer Object for Course entity.
 */
public class CourseDTO {

    @NotBlank(message = "Course code is required")
    @Pattern(regexp = "^[A-Z0-9]{2,10}$", message = "Course code must be 2-10 uppercase letters and numbers")
    private String code;

    @NotBlank(message = "Course title is required")
    @Size(max = 100, message = "Course title must not exceed 100 characters")
    private String title;

    @Size(max = 500, message = "Course description must not exceed 500 characters")
    private String description;

    private Set<StudentDTO> students = new HashSet<>();

    // Constructors
    public CourseDTO() {
    }

    public CourseDTO(String code, String title, String description) {
        this.code = code;
        this.title = title;
        this.description = description;
    }

    // Getters and setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentDTO> students) {
        this.students = students;
    }
} 