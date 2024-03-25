variable "cluster_name" {
  description = "the name of your stack, e.g. \"demo\""
}

variable "private_subnets" {
  description = "List of private subnet IDs"
}

variable "public_subnets" {
  description = "List of private subnet IDs"
}

variable "zone" {
  description = "AWS Region where the cluster will be deployed"
}

variable "account_id" {
  description = "AWS account ID"
}
