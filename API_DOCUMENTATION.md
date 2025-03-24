# Super Simple Scheduling System API Documentation

This document provides detailed information about all endpoints available in the Super Simple Scheduling System API.

## Base URL

```
http://localhost:8080/api
```

## Table of Contents

1. [Health Endpoints](#health-endpoints)
2. [Student Endpoints](#student-endpoints)
3. [Course Endpoints](#course-endpoints)

## Health Endpoints

### 1. Basic Health Check

- **Endpoint:** `GET /health`
- **Description:** Returns basic health information about the API
- **Sample Response:**

```json
{"service":"super-simple-scheduling-system","profiles":["dev"],"status":"UP","timestamp":"2025-03-24T14:22:09.487758542"}
```

### 2. Detailed Health Check

- **Endpoint:** `GET /health/details`
- **Description:** Returns detailed health information including environment details
- **Sample Response:**

```json
{"memory":"113MB","osVersion":"6.10.14-linuxkit","service":"super-simple-scheduling-system","javaVersion":"17.0.14","profiles":["dev"],"osName":"Linux","freeMemory":"66MB","status":"UP","timestamp":"2025-03-24T14:22:09.533465500"}
```

## Student Endpoints

### 1. Get All Students

- **Endpoint:** `GET /students`
- **Description:** Returns a list of all students
- **Sample Response:** (truncated)

```json
{"id":1,"firstName":"John","lastName":"Doe","email":"john.doe@example.com","courses":[]}
// ... more students
```

### 2. Get Student by ID

- **Endpoint:** `GET /students/{id}`
- **Description:** Returns a single student by ID
- **Parameters:**
  - `id` (path parameter): The student ID
- **Sample Response:**

```json
{"id":1,"firstName":"John","lastName":"Doe","email":"john.doe@example.com","courses":[]}
```

### 3. Get Paged Students

- **Endpoint:** `GET /students/paged?page={page}&size={size}`
- **Description:** Gets a paginated list of students
- **Parameters:**
  - `page` (query parameter): Page number (0-based)
  - `size` (query parameter): Page size
  - `sort` (query parameter, optional): Sort field and direction (e.g., "firstName,asc")
- **Sample Response:**

```json
Rate limit exceeded. Please try again later.
```

### 4. Search Students

- **Endpoint:** `GET /students/search?query={searchTerm}`
- **Description:** Searches for students by name or email
- **Parameters:**
  - `query` (query parameter): The search term

### 5. Create New Student

- **Endpoint:** `POST /students`
- **Description:** Creates a new student
- **Request Body:**

```json
{
  "firstName": "Test",
  "lastName": "User",
  "email": "test.user@example.com"
}
```

### 6. Update Student

- **Endpoint:** `PUT /students/{id}`
- **Description:** Updates an existing student
- **Parameters:**
  - `id` (path parameter): The student ID
- **Request Body:**

```json
{
  "firstName": "Updated",
  "lastName": "Name",
  "email": "updated.email@example.com"
}
```

### 7. Delete Student

- **Endpoint:** `DELETE /students/{id}`
- **Description:** Deletes a student by ID
- **Parameters:**
  - `id` (path parameter): The student ID

### 8. Get Student Courses

- **Endpoint:** `GET /students/{id}/courses`
- **Description:** Gets all courses for a student
- **Parameters:**
  - `id` (path parameter): The student ID

### 9. Assign Courses to Student

- **Endpoint:** `POST /students/{id}/courses`
- **Description:** Assigns multiple courses to a student
- **Parameters:**
  - `id` (path parameter): The student ID
- **Request Body:**

```json
["CS101", "MATH101", "PHYS101"]
```

## Course Endpoints

### 1. Get All Courses

- **Endpoint:** `GET /courses`
- **Description:** Returns a list of all courses
- **Sample Response:** (truncated)

```json
{"code":"CS101","title":"Introduction to Programming","description":"Fundamental concepts of programming using Java.","students":[]}
// ... more courses
```

### 2. Get Course by Code

- **Endpoint:** `GET /courses/{code}`
- **Description:** Returns a single course by code
- **Parameters:**
  - `code` (path parameter): The course code
- **Sample Response:**

```json
{"code":"CS101","title":"Introduction to Programming","description":"Fundamental concepts of programming using Java.","students":[]}
```

### 3. Create New Course

- **Endpoint:** `POST /courses`
- **Description:** Creates a new course
- **Request Body:**

```json
{
  "code": "TEST101",
  "title": "Test Course",
  "description": "This is a test course"
}
```

### 4. Update Course

- **Endpoint:** `PUT /courses/{code}`
- **Description:** Updates an existing course
- **Parameters:**
  - `code` (path parameter): The course code
- **Request Body:**

```json
{
  "code": "TEST101",
  "title": "Updated Course Title",
  "description": "Updated course description"
}
```

### 5. Delete Course

- **Endpoint:** `DELETE /courses/{code}`
- **Description:** Deletes a course by code
- **Parameters:**
  - `code` (path parameter): The course code

### 6. Get Course Students

- **Endpoint:** `GET /courses/{code}/students`
- **Description:** Gets all students enrolled in a course
- **Parameters:**
  - `code` (path parameter): The course code

### 7. Get Courses by Student ID

- **Endpoint:** `GET /courses/students/{studentId}`
- **Description:** Gets all courses a student is enrolled in
- **Parameters:**
  - `studentId` (path parameter): The student ID

### 8. Get Courses Not Taken by Student

- **Endpoint:** `GET /courses/not-taken/{studentId}`
- **Description:** Gets all courses a student is not enrolled in
- **Parameters:**
  - `studentId` (path parameter): The student ID

## Error Handling

### Common Error Responses

#### 1. Not Found (404)
```json
{
  "status": 404,
  "message": "Resource not found",
  "timestamp": "2025-03-24T14:21:13.718643623",
  "validationErrors": null
}
```

#### 2. Bad Request (400)
```json
{
  "status": 400,
  "message": "Invalid request",
  "timestamp": "2025-03-24T14:21:13.718643623",
  "validationErrors": {
    "email": "must be a valid email address",
    "firstName": "must not be blank"
  }
}
```

#### 3. Internal Server Error (500)
```json
{
  "status": 500,
  "message": "Internal server error",
  "timestamp": "2025-03-24T14:21:13.718643623",
  "validationErrors": null
}
```

## Validation Rules

### Student Validation
- firstName: Required, max length 50
- lastName: Required, max length 50
- email: Required, valid email format, max length 100

### Course Validation
- code: Required, pattern `^[A-Z0-9]{2,10}$` (2-10 uppercase letters or numbers)
- title: Required, max length 100
- description: Optional, max length 500
