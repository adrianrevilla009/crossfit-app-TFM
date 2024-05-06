terraform {
  required_version = ">= 0.13"
  required_providers {
    kubectl = {
      source  = "gavinbunney/kubectl"
      version = ">= 1.14.0"
    }
    aws = {
      source  = "hashicorp/aws"
      version = ">= 4.53.0"
    }
    time = {
      source  = "hashicorp/time"
      version = ">= 0.9.1"
    }
  }
#  backend "s3" {
#    bucket         = "terraform-crossfitapp-state-bucket"
#    key            = "terraform.tfstate"
#    region         = "us-west-1"
#    encrypt        = true
#    # kms_key_id     = "arn:aws:kms:us-west-1:211125336619:key/a465293e-62e0-4d22-b38b-70a54f2448a5" Esto es de pago
#    # dynamodb_table = "terraform-state-lock-table"
#    profile        = "default"
#  }
}

provider "aws" {
  region  = var.zone
  profile = "default"
}

data "aws_caller_identity" "current" {}

module "ec2" {
  source          = "./ec2"
  cluster_name    = var.project_name
  # private_subnets = module.vpc.aws_subnets_private
  # public_subnets  = module.vpc.aws_subnets_public
  zone            = var.zone
  account_id      = data.aws_caller_identity.current.account_id
  key_name        = ""
  subnet_id       = ""
  ami             = var.ami
}



