# 🧠 Product Requirements Document (PRD)

## 📌 Project: Super Simple Scheduling System (S4)

### 📖 Overview
The Super Simple Scheduling System (S4) is designed to streamline the process of course assignment for students, targeting educational institutions and training programs. The system aims to simplify course management and enhance student engagement by providing an easy-to-use interface for both students and administrators. Real-world use cases include managing course enrollments in schools, universities, and online learning platforms.

---

## 🎯 MVP Goals

- ✅ Full REST API for Students & Courses
- ✅ Many-to-many relationship handling
- ✅ Swagger UI for API testing
- ✅ React + TypeScript UI
- ✅ Dockerized backend
- ✅ Terraform-based deployment on AWS EC2 (Free Tier)
- ✅ GitHub Actions CI/CD
- ⏳ Timeframe expectations (2–3 hours per spec)
- 🚫 Non-goals (e.g., No authentication, no persistence beyond mock/H2)
- 📦 Deliverables (code, docs, deploy)

---

## 🧱 Architecture Overview
The architecture follows a layered approach: Controllers handle incoming requests, Services contain business logic, DTOs (Data Transfer Objects) are used for data exchange, and data is stored in-memory or using H2 for this MVP. DTOs help avoid bidirectional JSON issues, ensuring a clean separation of concerns. Additionally, Swagger facilitates contract-first development, making API documentation and testing more efficient.

---

## 🧩 Core Models

### Student
| Field        | Type   |
|--------------|--------|
| id           | Long   |
| firstName    | String |
| lastName     | String |
| email        | String |
| courses      | List<CourseDTO> |

### Course
| Field        | Type   |
|--------------|--------|
| code         | String |
| title        | String |
| description  | String |
| students     | List<StudentDTO> |

---

## 📦 REST API Endpoints

### Students
- `GET /students`
- `POST /students`
- `PUT /students/{id}`
- `DELETE /students/{id}`
- `GET /students/{id}/courses`
- `POST /students/{id}/courses`

### Courses
- `GET /courses`
- `POST /courses`
- `PUT /courses/{code}`
- `DELETE /courses/{code}`
- `GET /courses/{code}/students`

---

## 🛠️ Tech Stack

| Layer     | Tech                                |
|-----------|-------------------------------------|
| Backend   | Java 17, Spring Boot, JPA, Swagger  |
| Frontend  | React, TypeScript, Vite, Axios      |
| Infra     | AWS (EC2), Terraform, Docker        |
| CI/CD     | GitHub Actions                      |
| Additional Tools | Swagger/OpenAPI, Terraform modules, Docker Compose |

---

## 🧪 Swagger Integration

> **Dependency:**

```groovy
implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0'

## 🔮 Future Roadmap

- User authentication and role-based access (JWT)
- Full PostgreSQL persistence with RDS
- React Admin Panel with sorting/filtering
- CloudWatch logging + monitoring
- GitHub Actions CI/CD with container image publishing
- ECS/Lightsail Docker hosting
- LTI/EdTech platform integrations
- Student performance tracking and analytics
- Mobile support via React Native
```