package com.goldenglowitsolutions.simpleschedulingsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Configuration for API rate limiting.
 * Uses a simple implementation with AtomicInteger since Bucket4j dependency has issues.
 */
@Configuration
public class RateLimitingConfig {

    @Value("${app.ratelimiting.capacity:20}")
    private int capacity;

    @Value("${app.ratelimiting.period-minutes:1}")
    private int periodMinutes;

    /**
     * Creates a rate limiting container that allows a configurable number of requests per minute.
     *
     * @return the rate limiting container
     */
    @Bean
    public RateLimitingContainer rateLimitingContainer() {
        return new RateLimitingContainer(capacity, periodMinutes);
    }

    /**
     * Simple container class for rate limiting.
     */
    public static class RateLimitingContainer {
        private final AtomicInteger tokens;
        private final int capacity;
        private final int periodMinutes;
        private long lastRefillTimestamp;

        public RateLimitingContainer(int capacity, int periodMinutes) {
            this.tokens = new AtomicInteger(capacity);
            this.capacity = capacity;
            this.periodMinutes = periodMinutes;
            this.lastRefillTimestamp = System.currentTimeMillis();
        }

        /**
         * Try to consume a token from the bucket.
         *
         * @param tokenCount number of tokens to consume
         * @return true if successful, false otherwise
         */
        public synchronized boolean tryConsume(int tokenCount) {
            refillIfNeeded();
            int currentTokens = tokens.get();
            if (currentTokens >= tokenCount) {
                return tokens.compareAndSet(currentTokens, currentTokens - tokenCount);
            }
            return false;
        }

        /**
         * Get the available tokens.
         *
         * @return number of available tokens
         */
        public synchronized int getAvailableTokens() {
            refillIfNeeded();
            return tokens.get();
        }

        /**
         * Refill the tokens if the refill period has elapsed.
         */
        private void refillIfNeeded() {
            long now = System.currentTimeMillis();
            long millisSinceLastRefill = now - lastRefillTimestamp;
            long refillPeriodMillis = Duration.ofMinutes(periodMinutes).toMillis();
            
            if (millisSinceLastRefill >= refillPeriodMillis) {
                tokens.set(capacity);
                lastRefillTimestamp = now;
            }
        }

        /**
         * Get the capacity of the container.
         *
         * @return the capacity
         */
        public int getCapacity() {
            return capacity;
        }

        /**
         * Get the refill period in minutes.
         *
         * @return the refill period
         */
        public int getPeriodMinutes() {
            return periodMinutes;
        }
    }
} 