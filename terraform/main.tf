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
  backend "s3" {
    bucket         = "terraform-crossfitapp-state-bucket"
    key            = "terraform.tfstate"
    region         = "us-west-1"
    encrypt        = true
    # kms_key_id     = "arn:aws:kms:us-west-1:211125336619:key/a465293e-62e0-4d22-b38b-70a54f2448a5" Esto es de pago
    dynamodb_table = "terraform-state-lock-table"
    profile        = "default"
  }
}

provider "aws" {
  region  = var.zone
  profile = "default"
}

data "aws_caller_identity" "current" {}

module "vpc" {
  source                     = "./vpc"
  vpc_cidr                   = var.vpc_cidr
  vpc_name                   = var.vpc_name
  cluster_name               = var.project_name
  public_subnets_cidr        = var.public_subnets_cidr
  availability_zones_public  = var.availability_zones_public
  private_subnets_cidr       = var.private_subnets_cidr
  availability_zones_private = var.availability_zones_private
  cidr_block-nat_gw          = var.cidr_block-nat_gw
  cidr_block-internet_gw     = var.cidr_block-internet_gw
}

module "hosted_zone_and_certs" {
  source       = "./hosted-zone-and-certs"
  project_name = var.project_name
}

module "ecr" {
  source = "./ecr"
}

module "eks" {
  source          = "./eks"
  cluster_name    = var.project_name
  private_subnets = module.vpc.aws_subnets_private
  public_subnets  = module.vpc.aws_subnets_public
  zone            = var.zone
  account_id      = data.aws_caller_identity.current.account_id
}
module "database" {
  source            = "./database"
  secret_id         = var.secret_id
  secret_pass       = var.secret_pass
  identifier        = var.identifier
  allocated_storage = var.allocated_storage
  storage_type      = var.storage_type
  engine            = var.engine
  engine_version    = var.engine_version
  instance_class    = var.instance_class
  database_name     = var.database_name
  vpc_id            = module.vpc.vpc_id
  vpc_cidr          = var.vpc_cidr
  private_subnets   = module.vpc.aws_subnets_private
}
module "kubernetes" {
  source                        = "./kubernetes"
  cluster_id                    = module.eks.cluster_id
  vpc_id                        = module.vpc.vpc_id
  cluster_name                  = module.eks.cluster_name
  zone                          = var.zone
  db_endpoint                   = module.database.db_instance_endpoint
  db_secret_id                  = var.secret_id
  db_secret_pass                = var.secret_pass
  account_id                    = data.aws_caller_identity.current.account_id
  hosted_zone_id                = module.hosted_zone_and_certs.hosted_zone_id
  project_name                  = var.project_name
  github_username               = var.github_username
  github_token                  = var.github_token
  github_repository_url         = var.github_repository_url
  load_balancer_certificate_arn = module.hosted_zone_and_certs.aws_acm_certificate_arn
}
module "s3" {
  source                     = "./s3"
  project_name               = var.project_name
  hosted_zone_id             = module.hosted_zone_and_certs.hosted_zone_id
  cloudfront_certificate_arn = module.hosted_zone_and_certs.cloudfront_certificate_arn
}



