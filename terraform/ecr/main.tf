# Este es el tipo de recurso de Terraform utilizado para crear un repositorio en Amazon ECR. "api"
# es el nombre dado a este recurso en el bloque de código, que se puede usar para referirse a él
# en otras partes del archivo de configuración.
resource "aws_ecr_repository" "api" {
  name = "crossfitapp-api"
  force_delete = true
  image_scanning_configuration {
    scan_on_push = true
  }
}

