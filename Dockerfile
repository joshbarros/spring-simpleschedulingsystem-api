# Build stage
FROM amazoncorretto:17-alpine as build
WORKDIR /workspace/app

# Copy gradle files
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Ensure gradlew is executable
RUN chmod +x ./gradlew

# Download dependencies
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src src

# Build the application with bootJar task to ensure proper manifest
RUN ./gradlew bootJar --no-daemon

# Runtime stage
FROM amazoncorretto:17-alpine
WORKDIR /app
VOLUME /tmp

# Copy the JAR file from the build stage
COPY --from=build /workspace/app/build/libs/app.jar app.jar

# Run the application with Spring Boot specific java commands
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar"]

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s CMD wget -qO- http://localhost:8080/api/health || exit 1