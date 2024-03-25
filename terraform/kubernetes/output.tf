output "load_balancer_hostname" {
  value = data.kubernetes_service.nginx_ingress_controller.status.0.load_balancer.0.ingress.0.hostname
}
