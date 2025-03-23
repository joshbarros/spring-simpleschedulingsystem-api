# ======================================================
# security.tf - Security Group Configuration
# ======================================================

# Create Security Group for the EC2 instance
resource "aws_security_group" "spring_app_sg" {
  name        = "${var.instance_name}-security-group"
  description = "Security group for Spring Boot application"

  # SSH access from anywhere (for development purposes)
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    description = "SSH access"
  }

  # Application port access
  ingress {
    from_port   = var.app_port
    to_port     = var.app_port
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
    description = "Application port access"
  }

  # Outbound internet access
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
    description = "Allow all outbound traffic"
  }

  tags = {
    Name      = "${var.instance_name}-security-group"
    ManagedBy = "Terraform"
  }
} 