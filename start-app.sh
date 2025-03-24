#!/bin/bash

# Display fancy header
echo "========================================================"
echo "     Super Simple Scheduling System - Docker Control    "
echo "========================================================"

# Stop any running containers
echo "Stopping any running containers..."
docker compose down

# Clean up any dangling images
echo "Cleaning up dangling images..."
docker image prune -f

# Build and start the application
echo "Building and starting the application..."
docker compose up --build -d

# Wait for the application to start
echo "Waiting for the application to start..."
sleep 10

# Check the status of the container
echo "Container status:"
docker ps | grep s4-api

# Test the application
echo -e "\nTesting the API health endpoint..."
curl -s http://localhost:8080/api/health | jq || echo "Health endpoint returned: $(curl -s http://localhost:8080/api/health)"

echo -e "\nTesting the API students endpoint..."
curl -s http://localhost:8080/api/students | head -c 200 | jq || echo "Students endpoint returned the first part of the response (truncated for readability)"

echo -e "\nApplication logs (last 10 lines):"
docker logs s4-api --tail 10

echo -e "\nApplication is now running at http://localhost:8080/api"
echo "========================================================" 