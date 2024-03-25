variable "cluster_id" {
  description = "Put your cluster id here"
}

variable "vpc_id" {
  description = "put your vpc id"
}

variable "cluster_name" {
  description = "put your cluster name here"
}

variable "db_endpoint" {
  description = "Endpoint for the RDS instance"
}

variable "zone" {
  description = "AWS Region where the cluster will be deployed"
}

variable "account_id" {
  description = "AWS account ID"
}

variable "db_secret_id" {
  description = "RDS instance secret id"
}

variable "db_secret_pass" {
  description = "RDS instance secret pass"
}

variable "hosted_zone_id" {
  description = "Enter Route53 hosted zone id"
}

variable "project_name" {
  description = "Enter project's name"
}

variable "github_token" {
  description = "Enter github token"
}

variable "github_username" {
  description = "Enter github username"
}

variable "github_repository_url" {
  description = "Enter github repository url"
}

variable "load_balancer_certificate_arn" {
  description = "Enter load balancer certificate arn"
}
