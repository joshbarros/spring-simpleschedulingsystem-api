# ======================================================
# outputs.tf - Output Definitions
# ======================================================

output "instance_public_ip" {
  description = "Public IP address of the EC2 instance"
  value       = aws_instance.spring_app_instance.public_ip
}

output "application_url" {
  description = "URL to access the Spring Boot application"
  value       = "http://${aws_instance.spring_app_instance.public_ip}:${var.app_port}"
}

output "ssh_command" {
  description = "SSH command to connect to the EC2 instance"
  value       = "ssh -i ~/.ssh/${var.key_pair_name}.pem ec2-user@${aws_instance.spring_app_instance.public_ip}"
}

output "instance_id" {
  description = "ID of the EC2 instance"
  value       = aws_instance.spring_app_instance.id
}

output "ami_id" {
  description = "AMI ID used for the EC2 instance"
  value       = aws_instance.spring_app_instance.ami
} 