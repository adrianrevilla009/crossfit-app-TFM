output "api_repo_url" {
  value = module.ecr.api_repo_url
}

output "load_balancer_hostname" {
  value = module.kubernetes.load_balancer_hostname
}
