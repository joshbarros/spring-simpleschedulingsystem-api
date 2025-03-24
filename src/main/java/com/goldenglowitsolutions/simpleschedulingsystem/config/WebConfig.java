package com.goldenglowitsolutions.simpleschedulingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration to handle Cross-Origin Resource Sharing (CORS) settings.
 * This allows the frontend application to communicate with the backend API.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Set allowed origins explicitly - don't use wildcard with credentials
        config.addAllowedOrigin("http://localhost:8080");
        
        // Allow credentials (cookies, authorization headers, etc.)
        config.setAllowCredentials(true);
        
        // Allow all headers and methods
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        
        // Register this configuration for all routes
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
} 