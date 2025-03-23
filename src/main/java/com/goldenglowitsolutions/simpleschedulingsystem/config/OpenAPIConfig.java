package com.goldenglowitsolutions.simpleschedulingsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for OpenAPI documentation.
 */
@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Super Simple Scheduling System API")
                        .description("REST API for managing students, courses, and their assignments")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Golden Glow IT Solutions")
                                .email("contact@goldenglowitsolutions.com")
                                .url("https://goldenglowitsolutions.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
} 