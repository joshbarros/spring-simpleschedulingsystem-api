package com.goldenglowitsolutions.simpleschedulingsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for health and status checks.
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    /**
     * Basic health check endpoint.
     * @return Status information about the application.
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("service", "Super Simple Scheduling System API");
        return ResponseEntity.ok(response);
    }
} 