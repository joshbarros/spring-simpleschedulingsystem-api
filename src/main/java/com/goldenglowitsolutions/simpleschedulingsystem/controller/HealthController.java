package com.goldenglowitsolutions.simpleschedulingsystem.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Controller for health and status checks.
 */
@RestController
@RequestMapping("/health")
public class HealthController {
    
    private final Environment environment;
    
    @Value("${spring.application.name:super-simple-scheduling-system}")
    private String applicationName;
    
    private final Optional<BuildProperties> buildProperties;
    
    public HealthController(Environment environment, Optional<BuildProperties> buildProperties) {
        this.environment = environment;
        this.buildProperties = buildProperties;
    }

    /**
     * Basic health check endpoint.
     * @return Status information about the application.
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("service", applicationName);
        response.put("profiles", Arrays.asList(environment.getActiveProfiles()));
        
        buildProperties.ifPresent(props -> {
            Map<String, String> buildInfo = new HashMap<>();
            buildInfo.put("version", props.getVersion());
            buildInfo.put("artifact", props.getArtifact());
            buildInfo.put("name", props.getName());
            buildInfo.put("time", props.getTime().toString());
            response.put("build", buildInfo);
        });
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Detailed health check endpoint.
     * @return Detailed information about the application.
     */
    @GetMapping("/details")
    public ResponseEntity<Map<String, Object>> healthDetails() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("service", applicationName);
        response.put("profiles", Arrays.asList(environment.getActiveProfiles()));
        response.put("javaVersion", System.getProperty("java.version"));
        response.put("osName", System.getProperty("os.name"));
        response.put("osVersion", System.getProperty("os.version"));
        response.put("memory", Runtime.getRuntime().totalMemory() / 1024 / 1024 + "MB");
        response.put("freeMemory", Runtime.getRuntime().freeMemory() / 1024 / 1024 + "MB");
        
        return ResponseEntity.ok(response);
    }
} 