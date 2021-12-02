terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.67"
    }
  }
  required_version = ">= 0.14.9"
  backend "s3" {
    bucket = "laundry-terraform-state"
    key = "global/s3/terraform.tfstate"
    region = "ap-southeast-1"

    dynamodb_table = "laundry-terraform-locks"
    encrypt = true
  }
}
provider "aws" {
  profile = "default"
  region  = "ap-southeast-1"
}

#resource "aws_s3_bucket" "laundry-terraform-state" {
#  bucket = "laundry-terraform-state"
#  versioning {
#    enabled = true
#  }
#  server_side_encryption_configuration {
#    rule {
#      apply_server_side_encryption_by_default {
#        sse_algorithm = "AES256"
#      }
#    }
#  }
#}
#resource "aws_dynamodb_table" "laundry_terraform_locks" {
#  name         = "laundry-terraform-locks"
#  billing_mode = "PAY_PER_REQUEST"
#  hash_key     = "LockID"
#  attribute {
#    name = "LockID"
#    type = "S"
#  }
#}

resource "aws_s3_bucket" "s3_bucket_laundry" {
  bucket = "laundry-prod"
  acl    = "private"
}

resource "aws_s3_bucket_object" "s3_bucket_object_laundry" {
  bucket = aws_s3_bucket.s3_bucket_laundry.id
  key    = "beanstalk/laundry"
  source = "target/Laundry-BE.jar"
}

resource "aws_elastic_beanstalk_application" "beanstalk_laundry" {
  name        = "laundry"
  description = "beanstalk laundry app"
}

resource "aws_elastic_beanstalk_application_version" "beanstalk_laundry_version" {
  name        = "LaundryBackEnd-0.0.1"
  application = aws_elastic_beanstalk_application.beanstalk_laundry.name
  bucket      = aws_s3_bucket.s3_bucket_laundry.id
  key         = aws_s3_bucket_object.s3_bucket_object_laundry.id
}

resource "aws_elastic_beanstalk_environment" "beanstalk_laundry_env" {
  application         = aws_elastic_beanstalk_application.beanstalk_laundry.name
  name                = "laundry-prod"
  solution_stack_name = "64bit Amazon Linux 2 v3.2.8 running Corretto 11"
  version_label       = aws_elastic_beanstalk_application_version.beanstalk_laundry_version.name

  setting {
    name      = "SERVER_PORT"
    namespace = "aws:elasticbeanstalk:application:environment"
    value     = "5000"
  }

  setting {
    name = "InstanceTypes"
    namespace = "aws:ec2:instances"
    value     = "t2.micro"
  }
  setting {
    namespace = "aws:autoscaling:launchconfiguration"
    name      = "IamInstanceProfile"
    value     = "aws-elasticbeanstalk-ec2-role"
  }
}
