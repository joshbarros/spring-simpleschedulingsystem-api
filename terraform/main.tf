# ======================================================
# main.tf - Main Terraform Configuration
# ======================================================

# Configure the AWS Provider
terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
  required_version = ">= 1.2.0"
}

# Configure AWS region
provider "aws" {
  region = var.aws_region
}

# Include the EC2 instance and security group resources
# These are defined in their respective files: ec2.tf and security.tf 