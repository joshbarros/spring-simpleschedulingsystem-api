package com.goldenglowitsolutions.simpleschedulingsystem.interceptor;

import com.goldenglowitsolutions.simpleschedulingsystem.config.RateLimitingConfig.RateLimitingContainer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor for applying rate limiting to API requests.
 */
@Component
@RequiredArgsConstructor
public class RateLimitingInterceptor implements HandlerInterceptor {

    private final RateLimitingContainer rateLimitingContainer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Check if request can be consumed from the rate limiting container
        if (rateLimitingContainer.tryConsume(1)) {
            // Allow the request
            return true;
        } else {
            // Reject the request with 429 Too Many Requests
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Rate limit exceeded. Please try again later.");
            return false;
        }
    }
} 