package com.goldenglowitsolutions.simpleschedulingsystem.config;

import com.goldenglowitsolutions.simpleschedulingsystem.interceptor.RateLimitingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for Spring MVC to register interceptors.
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final RateLimitingInterceptor rateLimitingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Register rate limiting interceptor for all API endpoints
        registry.addInterceptor(rateLimitingInterceptor)
                .addPathPatterns("/students/**", "/courses/**");
    }
} 