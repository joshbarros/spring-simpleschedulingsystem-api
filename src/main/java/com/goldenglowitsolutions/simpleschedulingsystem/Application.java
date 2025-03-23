package com.goldenglowitsolutions.simpleschedulingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main application class for the Simple Scheduling System.
 * 
 * Features:
 * - RESTful APIs for managing students and courses
 * - Caching for improved performance
 * - Metrics and monitoring with Micrometer
 * - API rate limiting for security
 */
@SpringBootApplication
@EnableCaching
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
} 