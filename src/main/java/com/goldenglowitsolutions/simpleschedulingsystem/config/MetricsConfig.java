package com.goldenglowitsolutions.simpleschedulingsystem.config;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Configuration for metrics using Micrometer.
 */
@Configuration
@EnableAspectJAutoProxy
public class MetricsConfig {

    /**
     * Configures the timed aspect for method-level timing metrics.
     *
     * @param registry the meter registry
     * @return the timed aspect
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
} 