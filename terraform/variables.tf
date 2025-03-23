# ======================================================
# variables.tf - Variable Definitions
# ======================================================

variable "aws_region" {
  description = "AWS region for the resources"
  type        = string
  default     = "us-east-1"  # Free tier eligible region
}

variable "instance_name" {
  description = "Name for the EC2 instance"
  type        = string
  default     = "spring-scheduling-system"
}

variable "instance_type" {
  description = "EC2 instance type"
  type        = string
  default     = "t2.micro"  # Free tier eligible
}

variable "key_pair_name" {
  description = "Name of the AWS key pair to use for SSH access"
  type        = string
  default     = "spring-app-key"  # Replace with your key pair name
}

variable "app_port" {
  description = "Port on which the Spring Boot application runs"
  type        = number
  default     = 8080
}

variable "app_jar_url" {
  description = "URL to download the Spring Boot application JAR file"
  type        = string
  default     = "https://github.com/user/repo/releases/download/v1.0.0/app.jar"  # Replace with actual JAR URL
} 