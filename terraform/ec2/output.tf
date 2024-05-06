# Output para mostrar la IP pública de la instancia
output "public_ip" {
  value = aws_instance.ec2.public_ip
}
