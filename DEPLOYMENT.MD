# S4 Deployment Guide

This document provides instructions for deploying the Super Simple Scheduling System (S4) to various environments.

## Local Deployment

### Prerequisites
- Java 17 or higher
- Gradle
- Git

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/spring-simpleschedulingsystem-api.git
   cd spring-simpleschedulingsystem-api
   ```

2. Build the application:
   ```bash
   ./gradlew clean build
   ```

3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

4. The application will be available at:
   - API: http://localhost:8080/api
   - Swagger UI: http://localhost:8080/api/swagger-ui.html
   - H2 Console: http://localhost:8080/api/h2-console

## Docker Deployment

### Prerequisites
- Docker
- Docker Compose (optional)

### Steps
1. Build the Docker image:
   ```bash
   docker build -t s4-api:latest .
   ```

2. Run the Docker container:
   ```bash
   docker run -p 8080:8080 s4-api:latest
   ```

3. For persistent data using Docker Compose:
   
   Create a `docker-compose.yml` file:
   ```yaml
   version: '3.8'
   services:
     api:
       image: s4-api:latest
       ports:
         - "8080:8080"
       environment:
         - SPRING_PROFILES_ACTIVE=prod
         - SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/s4db
         - SPRING_DATASOURCE_USERNAME=postgres
         - SPRING_DATASOURCE_PASSWORD=password
       depends_on:
         - db
     db:
       image: postgres:14
       ports:
         - "5432:5432"
       environment:
         - POSTGRES_DB=s4db
         - POSTGRES_USER=postgres
         - POSTGRES_PASSWORD=password
       volumes:
         - postgres_data:/var/lib/postgresql/data
   volumes:
     postgres_data:
   ```

4. Run with Docker Compose:
   ```bash
   docker-compose up -d
   ```

## AWS Deployment

### Prerequisites
- AWS account
- Terraform
- AWS CLI configured

### Infrastructure Setup with Terraform
1. Navigate to the terraform directory:
   ```bash
   cd terraform
   ```

2. Initialize Terraform:
   ```bash
   terraform init
   ```

3. Plan the deployment:
   ```bash
   terraform plan -out=tfplan
   ```

4. Apply the infrastructure changes:
   ```bash
   terraform apply tfplan
   ```

5. The output will provide the EC2 instance public IP address and other resource details.

### Manual Deployment to EC2

1. Build the application:
   ```bash
   ./gradlew clean build
   ```

2. Copy the JAR file to the EC2 instance:
   ```bash
   scp -i /path/to/your-key.pem build/libs/simpleschedulingsystem-0.0.1-SNAPSHOT.jar ec2-user@EC2_IP:/home/ec2-user/
   ```

3. SSH into the instance:
   ```bash
   ssh -i /path/to/your-key.pem ec2-user@EC2_IP
   ```

4. Run the application:
   ```bash
   java -jar simpleschedulingsystem-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
   ```

### CI/CD Pipeline (GitHub Actions)

1. The repository includes a GitHub Actions workflow in `.github/workflows/deploy.yml` that:
   - Builds the application
   - Runs tests
   - Creates a Docker image
   - Pushes to Amazon ECR
   - Deploys to EC2

2. Configure the following secrets in your GitHub repository:
   - `AWS_ACCESS_KEY_ID`
   - `AWS_SECRET_ACCESS_KEY`
   - `EC2_HOST`
   - `EC2_SSH_KEY`

3. Push changes to the main branch to trigger the workflow:
   ```bash
   git add .
   git commit -m "Update application"
   git push origin main
   ```

## Environment Configuration

### Development Environment
- Uses H2 in-memory database
- Automatic schema creation
- Sample data initialization
- Configuration: `application.properties`

### Production Environment
- Uses PostgreSQL database
- Requires database schema to be prepared
- Disables sample data initialization
- Configuration: `application-prod.properties`

### Environment Variables
The following environment variables can be used to override application properties:

- `SERVER_PORT`: Application port (default: 8080)
- `SPRING_DATASOURCE_URL`: Database connection URL
- `SPRING_DATASOURCE_USERNAME`: Database username
- `SPRING_DATASOURCE_PASSWORD`: Database password
- `APP_RATELIMITING_CAPACITY`: Rate limit capacity (default: 20)
- `APP_RATELIMITING_PERIOD_MINUTES`: Rate limit period in minutes (default: 1)

## Monitoring and Maintenance

### Health Checks
- Health endpoint: http://[host]:[port]/api/actuator/health
- Metrics endpoint: http://[host]:[port]/api/actuator/metrics
- Info endpoint: http://[host]:[port]/api/actuator/info

### Logging
- Logs are output to the console by default
- In production, consider configuring external logging (ELK stack, CloudWatch)
- Adjust log levels in application.properties:
  ```properties
  logging.level.root=INFO
  logging.level.com.goldenglowitsolutions=DEBUG
  ```

## Troubleshooting

### Common Issues
1. **Database Connection Failures**
   - Verify database credentials
   - Check network connectivity
   - Ensure database service is running

2. **Application Won't Start**
   - Check port availability
   - Review Java version
   - Examine logs for exceptions

3. **API Performance Issues**
   - Monitor system resources
   - Review database query performance
   - Check for memory leaks 