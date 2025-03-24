package com.goldenglowitsolutions.simpleschedulingsystem.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class HealthControllerTest {

    @Mock
    private Environment environment;

    @Mock
    private Optional<BuildProperties> buildProperties;

    @InjectMocks
    private HealthController healthController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(environment.getActiveProfiles()).thenReturn(new String[]{"test"});
    }

    @Test
    void healthCheck_ReturnsBasicHealthInfo() {
        // Act
        ResponseEntity<Map<String, Object>> response = healthController.healthCheck();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("UP", response.getBody().get("status"));
        assertNotNull(response.getBody().get("timestamp"));
        
        // Check that profiles are included
        @SuppressWarnings("unchecked")
        Iterable<String> profiles = (Iterable<String>) response.getBody().get("profiles");
        assertNotNull(profiles);
    }

    @Test
    void healthDetails_ReturnsDetailedHealthInfo() {
        // Act
        ResponseEntity<Map<String, Object>> response = healthController.healthDetails();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("UP", response.getBody().get("status"));
        assertNotNull(response.getBody().get("timestamp"));
        assertNotNull(response.getBody().get("javaVersion"));
        assertNotNull(response.getBody().get("osName"));
        assertNotNull(response.getBody().get("osVersion"));
        assertNotNull(response.getBody().get("memory"));
        assertNotNull(response.getBody().get("freeMemory"));
    }
} 