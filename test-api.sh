#!/bin/bash

# Define colors for better readability
GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Define the API base URL
API_BASE="http://localhost:8080/api"

# Test function
test_endpoint() {
    local method=$1
    local endpoint=$2
    local payload=$3
    local description=$4
    
    echo -e "\n${BLUE}Testing: ${method} ${endpoint}${NC}"
    echo -e "${YELLOW}Description: ${description}${NC}"
    
    if [ "$method" == "GET" ]; then
        response=$(curl -s -X GET "${API_BASE}${endpoint}")
        status=$?
    elif [ "$method" == "POST" ]; then
        response=$(curl -s -X POST "${API_BASE}${endpoint}" -H "Content-Type: application/json" -d "${payload}")
        status=$?
    elif [ "$method" == "PUT" ]; then
        response=$(curl -s -X PUT "${API_BASE}${endpoint}" -H "Content-Type: application/json" -d "${payload}")
        status=$?
    elif [ "$method" == "DELETE" ]; then
        response=$(curl -s -X DELETE "${API_BASE}${endpoint}")
        status=$?
    fi
    
    if [ $status -eq 0 ]; then
        if [ "$response" == "" ]; then
            echo -e "${YELLOW}Empty response (expected for DELETE)${NC}"
        else
            echo -e "${GREEN}Response:${NC} $(echo $response | head -c 300)..."
        fi
        echo -e "${GREEN}✅ Test passed!${NC}"
    else
        echo -e "${RED}❌ Test failed!${NC}"
    fi
}

# Banner
echo -e "${BLUE}=================================================================${NC}"
echo -e "${BLUE}          Super Simple Scheduling System - API Testing           ${NC}"
echo -e "${BLUE}=================================================================${NC}"

# 1. Health Endpoints
echo -e "\n${YELLOW}=== Health Endpoints ===${NC}"
test_endpoint "GET" "/health" "" "Basic health check"
test_endpoint "GET" "/health/details" "" "Detailed health check"

# 2. Student Endpoints
echo -e "\n${YELLOW}=== Student Endpoints ===${NC}"
test_endpoint "GET" "/students" "" "Get all students"
test_endpoint "GET" "/students/1" "" "Get student by ID (ID=1)"
test_endpoint "GET" "/students/paged?page=0&size=5" "" "Get paged students"
test_endpoint "GET" "/students/search?query=John" "" "Search students"

# Create a new student
NEW_STUDENT='{
    "firstName": "Test",
    "lastName": "User",
    "email": "test.user@example.com"
}'
test_endpoint "POST" "/students" "$NEW_STUDENT" "Create new student"

# Get the ID of the newly created student to use in other tests
LAST_STUDENT_ID=$(curl -s "${API_BASE}/students" | jq 'max_by(.id) | .id')
echo -e "${BLUE}Last student ID: ${LAST_STUDENT_ID}${NC}"

# Update the student we just created
UPDATE_STUDENT='{
    "firstName": "Updated",
    "lastName": "User",
    "email": "updated.user@example.com"
}'
test_endpoint "PUT" "/students/${LAST_STUDENT_ID}" "$UPDATE_STUDENT" "Update student"

# Get student courses
test_endpoint "GET" "/students/${LAST_STUDENT_ID}/courses" "" "Get student courses"

# Assign courses to student
COURSE_CODES='["CS101", "MATH101"]'
test_endpoint "POST" "/students/${LAST_STUDENT_ID}/courses" "$COURSE_CODES" "Assign courses to student"

# 3. Course Endpoints
echo -e "\n${YELLOW}=== Course Endpoints ===${NC}"
test_endpoint "GET" "/courses" "" "Get all courses"
test_endpoint "GET" "/courses/CS101" "" "Get course by code (CS101)"

# Create a new course
NEW_COURSE='{
    "code": "TEST101",
    "title": "Test Course",
    "description": "This is a test course"
}'
test_endpoint "POST" "/courses" "$NEW_COURSE" "Create new course"

# Update the course we just created
UPDATE_COURSE='{
    "code": "TEST101",
    "title": "Updated Test Course",
    "description": "This is an updated test course"
}'
test_endpoint "PUT" "/courses/TEST101" "$UPDATE_COURSE" "Update course"

# Get course students
test_endpoint "GET" "/courses/CS101/students" "" "Get students enrolled in course"

# Get courses by student ID
test_endpoint "GET" "/courses/students/${LAST_STUDENT_ID}" "" "Get courses by student ID"

# Get courses not taken by student
test_endpoint "GET" "/courses/not-taken/${LAST_STUDENT_ID}" "" "Get courses not taken by student"

# 4. Delete the test data
echo -e "\n${YELLOW}=== Cleanup Tests ===${NC}"
test_endpoint "DELETE" "/courses/TEST101" "" "Delete test course"
test_endpoint "DELETE" "/students/${LAST_STUDENT_ID}" "" "Delete test student"

echo -e "\n${BLUE}=================================================================${NC}"
echo -e "${GREEN}API Testing Completed!${NC}"
echo -e "${BLUE}=================================================================${NC}" 