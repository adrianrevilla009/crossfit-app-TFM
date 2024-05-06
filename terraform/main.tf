terraform {
  required_providers {
    aws = {
      source = "hashicorp/aws"
      Version = "~>3.27"
    }
  }

  required_version = ">=0.14.9"

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



