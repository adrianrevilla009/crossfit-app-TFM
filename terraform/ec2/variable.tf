variable "cluster_name" {
  description = "the name of your stack, e.g. \"demo\""
}

variable "ami" {
  description = "AMI ID"
}

variable "zone" {
  description = "AWS Region where the cluster will be deployed"
}

variable "account_id" {
  description = "AWS account ID"
}
