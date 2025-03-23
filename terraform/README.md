# S4 Infrastructure as Code

This directory contains Terraform configurations for deploying the S4 application to AWS infrastructure.

## Security Notice

⚠️ **IMPORTANT**: This directory contains configurations that may reference sensitive information. Follow these guidelines to maintain security:

1. **Never commit state files**: Terraform state files can contain sensitive information. They are excluded in `.gitignore` but double-check before committing.
2. **Use variables for sensitive data**: Never hardcode credentials, tokens, or other sensitive information.
3. **Use remote state storage** in production: Configure a secure backend like S3 with encryption and locking via DynamoDB.

## Prerequisites

- AWS CLI configured with appropriate credentials
- Terraform >= 1.0.0
- An AWS key pair for EC2 instance access

## Configuration

Create a `terraform.tfvars` file (which is excluded from git) to provide your specific variables:

```hcl
aws_region      = "us-east-1"
key_pair_name   = "your-key-pair"
instance_type   = "t2.micro"
app_name        = "s4-api"
environment     = "production"
```

## Usage

```bash
# Initialize Terraform
terraform init

# Plan changes
terraform plan -out=tfplan

# Apply changes
terraform apply tfplan

# Destroy resources when no longer needed
terraform destroy
```

## Resources Created

- VPC with public subnets
- Security groups for application
- EC2 instance(s) for hosting the application
- IAM roles and policies for the EC2 instance
- Optional: RDS PostgreSQL database (commented out in dev)

## Best Practices

1. Review the plan output carefully before applying
2. Use workspace to manage different environments
3. Tag all resources appropriately
4. Regularly update provider and module versions
5. Lock provider versions in configuration

## Troubleshooting

If you encounter issues:

1. Ensure AWS credentials are correctly configured
2. Verify network settings allow necessary traffic
3. Check EC2 instance logs: `ssh ec2-user@<instance-ip> cat /var/log/cloud-init-output.log`

## Infrastructure Components

- **EC2 Instance**: For hosting the application
- **RDS PostgreSQL**: Database for the application
- **S3 Bucket**: For static assets and backups
- **CloudWatch**: For monitoring and logging
- **IAM Roles**: For service permissions

## Modules

The infrastructure is organized into the following modules:

- `networking/`: VPC, subnets, security groups
- `compute/`: EC2 instances, load balancers
- `database/`: RDS instances
- `storage/`: S3 buckets
- `monitoring/`: CloudWatch configurations

## Outputs

After deployment, you'll receive outputs including:

- Application URL
- Database connection string
- S3 bucket names 