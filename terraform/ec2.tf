# ======================================================
# ec2.tf - EC2 Instance Configuration
# ======================================================

# Get the latest Amazon Linux 2023 AMI
data "aws_ami" "amazon_linux_2023" {
  most_recent = true
  owners      = ["amazon"]

  filter {
    name   = "name"
    values = ["al2023-ami-2023.*-x86_64"]
  }

  filter {
    name   = "virtualization-type"
    values = ["hvm"]
  }
}

# Create EC2 instance
resource "aws_instance" "spring_app_instance" {
  ami                    = data.aws_ami.amazon_linux_2023.id
  instance_type          = var.instance_type
  key_name               = var.key_pair_name
  vpc_security_group_ids = [aws_security_group.spring_app_sg.id]

  root_block_device {
    volume_size = 8
    volume_type = "gp2"
  }

  # Cloud-init user data script to install Docker and run Spring Boot app
  user_data = <<-EOF
    #!/bin/bash
    # Update system packages
    dnf update -y
    
    # Install Docker
    dnf install -y docker
    systemctl enable docker
    systemctl start docker
    
    # Create app directory
    mkdir -p /home/ec2-user/app
    cd /home/ec2-user/app
    
    # Try to download Spring Boot JAR file
    wget ${var.app_jar_url} -O app.jar || echo "Failed to download JAR, will use fallback"
    
    # Check if we have a valid JAR file
    if [ -s app.jar ]; then
      # Run Spring Boot app with Docker if JAR exists and is not empty
      docker run -d \
        --name spring-scheduling-system \
        -p ${var.app_port}:${var.app_port} \
        -v /home/ec2-user/app:/app \
        -w /app \
        openjdk:17-slim \
        java -jar app.jar
    else
      # Fallback to Nginx as a placeholder if JAR download fails
      echo "<html><body><h1>Spring Scheduling System</h1><p>This is a placeholder for the Spring Boot application.</p><p>The actual application JAR file could not be downloaded.</p></body></html>" > index.html
      
      docker run -d \
        --name web-placeholder \
        -p ${var.app_port}:80 \
        -v /home/ec2-user/app:/usr/share/nginx/html:ro \
        --restart always \
        nginx
    fi
    
    # Add ec2-user to the docker group so they can run docker commands without sudo
    usermod -aG docker ec2-user
  EOF

  tags = {
    Name        = var.instance_name
    Environment = "dev"
    Project     = "SimpleSchedulingSystem"
    ManagedBy   = "Terraform"
  }
} 