package com.goldenglowitsolutions.simpleschedulingsystem.controller;

import com.goldenglowitsolutions.simpleschedulingsystem.config.RateLimitingConfig.RateLimitingContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller to expose rate limit information.
 */
@RestController
@RequestMapping("/api/rate-limit")
@RequiredArgsConstructor
public class RateLimitController {

    private final RateLimitingContainer rateLimitingContainer;

    /**
     * Get current rate limit information.
     *
     * @return A map containing available tokens information
     */
    @GetMapping("/info")
    public Map<String, Object> getRateLimitInfo() {
        return Map.of(
                "availableTokens", rateLimitingContainer.getAvailableTokens(),
                "maxCapacity", rateLimitingContainer.getCapacity(),
                "refillPeriodMinutes", rateLimitingContainer.getPeriodMinutes()
        );
    }
} 