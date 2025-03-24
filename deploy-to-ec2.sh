#!/bin/bash

# Deployment script for Super Simple Scheduling System
# This script builds the application locally and deploys it to an EC2 instance

# Configuration - modify these variables
EC2_USER="ec2-user"
EC2_HOST="ec2-34-207-147-146.compute-1.amazonaws.com"
SSH_KEY_PATH="~/.ssh/spring-app-key.pem"
REMOTE_APP_PATH="~/app.jar"
PROFILE="aws"
PORT="8080"

# Display banner
echo "========================================================"
echo "     Super Simple Scheduling System - EC2 Deployer      "
echo "========================================================"
echo "Target: ${EC2_USER}@${EC2_HOST}"
echo "========================================================"

# Step 1: Build the application
echo -e "\n[1/5] Building the application with Gradle..."
./gradlew clean bootJar

# Verify the build was successful
if [ $? -ne 0 ]; then
    echo "❌ Build failed. Aborting deployment."
    exit 1
fi

# Step 2: Copy JAR file to EC2 instance
echo -e "\n[2/5] Copying JAR file to EC2 instance..."
scp -i ${SSH_KEY_PATH} build/libs/app.jar ${EC2_USER}@${EC2_HOST}:${REMOTE_APP_PATH}

# Verify the copy was successful
if [ $? -ne 0 ]; then
    echo "❌ Failed to copy JAR file to EC2 instance. Aborting deployment."
    exit 1
fi

# Step 3: Stop the running application on EC2
echo -e "\n[3/5] Stopping the running application on EC2..."
ssh -i ${SSH_KEY_PATH} ${EC2_USER}@${EC2_HOST} "ps aux | grep java | grep -v grep | awk '{print \$2}' | xargs -r kill -9"

# Step 4: Start the application on EC2
echo -e "\n[4/5] Starting the application on EC2..."
ssh -i ${SSH_KEY_PATH} ${EC2_USER}@${EC2_HOST} "nohup java -jar ${REMOTE_APP_PATH} --spring.profiles.active=${PROFILE} --server.port=${PORT} > ~/app.log 2>&1 &"

# Step 5: Verify the application is running
echo -e "\n[5/5] Verifying the application is running..."
sleep 10 # Wait for the application to start

# Check if the application is running
ssh -i ${SSH_KEY_PATH} ${EC2_USER}@${EC2_HOST} "ps aux | grep java"

# Check the application logs
echo -e "\nApplication logs (last 10 lines):"
ssh -i ${SSH_KEY_PATH} ${EC2_USER}@${EC2_HOST} "tail -n 10 ~/app.log"

# Test the API
echo -e "\nTesting the API health endpoint..."
curl -s http://${EC2_HOST}:${PORT}/api/health

echo -e "\n\n========================================================"
echo "     Deployment Complete!     "
echo "========================================================"
echo "The application is now running at http://${EC2_HOST}:${PORT}/api"
echo "To view logs: ssh -i ${SSH_KEY_PATH} ${EC2_USER}@${EC2_HOST} 'tail -f ~/app.log'"
echo "========================================================" 