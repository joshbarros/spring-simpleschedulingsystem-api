# Super Simple Scheduling System (S4)

A lightweight RESTful API system for managing courses and student assignments with a React frontend.

## 📋 Overview

S4 is a simple yet powerful scheduling system that allows educational institutions to:
- Manage student records
- Create and update course information
- Assign courses to students
- View course enrollment and student schedules

## 🚀 Features

- **RESTful API**: Complete CRUD operations for students and courses
- **Many-to-many Relationships**: Flexible course assignment system
- **Swagger Documentation**: Interactive API testing and documentation
- **React Frontend**: Modern TypeScript UI for easy management
- **Containerized**: Docker deployment for consistent environments
- **Infrastructure as Code**: Terraform scripts for AWS deployment
- **CI/CD Pipeline**: Automated deployment via GitHub Actions
- **Rate Limiting**: API rate limiting for security and stability

## 🛠️ Tech Stack

| Component    | Technologies                               |
|--------------|-------------------------------------------|
| Backend      | Java 17, Spring Boot, Spring Data JPA      |
| API Docs     | Swagger/OpenAPI 3.0                        |
| Database     | H2 Database (dev), PostgreSQL (prod)       |
| Frontend     | React, TypeScript, Vite                    |
| Deployment   | Docker, AWS EC2                            |
| IaC          | Terraform                                  |
| CI/CD        | GitHub Actions                             |

## 🏗️ Project Structure

```
spring-simpleschedulingsystem-api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/goldenglowitsolutions/simpleschedulingsystem/
│   │   │       ├── controller/     # REST controllers
│   │   │       ├── dto/            # Data Transfer Objects
│   │   │       ├── entity/         # JPA entities
│   │   │       ├── repository/     # Spring Data repositories
│   │   │       ├── service/        # Business logic
│   │   │       └── exception/      # Custom exceptions
│   │   └── resources/              # Application properties, static resources
│   └── test/                       # Test cases
├── frontend/                       # React frontend application
├── terraform/                      # Infrastructure as code
├── .github/workflows/              # CI/CD configuration
├── docker/                         # Docker configuration
└── docs/                           # Project documentation
```

## 🔧 Getting Started

### Prerequisites
- Java 17
- Maven or Gradle
- Docker (optional)
- Node.js and npm (for frontend)

### Local Development

```bash
# Clone the repository
git clone https://github.com/yourusername/spring-simpleschedulingsystem-api.git
cd spring-simpleschedulingsystem-api

# Run the application with Gradle
./gradlew bootRun
```

The application will start on http://localhost:8080 with the following available:
- API: http://localhost:8080/api/
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- H2 Console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:schedulingdb, Username: sa, Password: password)

### API Endpoints

#### Student Endpoints
- `GET /students` - Retrieve all students
- `POST /students` - Create a new student
- `PUT /students/{id}` - Update a student
- `DELETE /students/{id}` - Delete a student
- `GET /students/{id}/courses` - Get courses for a student
- `POST /students/{id}/courses` - Assign courses to a student

#### Course Endpoints
- `GET /courses` - Retrieve all courses
- `POST /courses` - Create a new course
- `PUT /courses/{code}` - Update a course
- `DELETE /courses/{code}` - Delete a course
- `GET /courses/{code}/students` - Get students enrolled in a course

## 🐳 Docker Deployment

```bash
# Build the Docker image
docker build -t s4-api .

# Run the Docker container
docker run -p 8080:8080 s4-api
```

## ☁️ AWS Deployment

The application can be deployed to AWS using the Terraform configuration in the `terraform` directory. See the [Terraform README](terraform/README.md) for details.

## 🚢 CI/CD Pipeline

The GitHub Actions workflow in `.github/workflows/deploy.yml` automates the build and deployment process:

1. Builds the application
2. Runs tests
3. Creates a Docker image
4. Deploys to AWS

## 📚 Documentation

- [API Documentation](http://localhost:8080/swagger-ui/index.html) (when running locally)
- [Product Requirements Document](PRD.MD)
- [Task List](TODO.MD)
- [Development Roadmap](ROADMAP.MD)

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Contact

Golden Glow IT Solutions - contact@goldenglowitsolutions.com

Project Link: https://github.com/yourusername/spring-simpleschedulingsystem-api 

### Rate Limiting

The API implements rate limiting to protect against abuse and ensure fair usage:

- Default limit: 20 requests per minute per client
- When the limit is exceeded, the API returns a 429 Too Many Requests status code
- Rate limit information is available at: http://localhost:8080/api/rate-limit/info

### Actuator Endpoints

Health and monitoring information is available at:

- http://localhost:8080/actuator/health
- http://localhost:8080/actuator/info
- http://localhost:8080/actuator/metrics 