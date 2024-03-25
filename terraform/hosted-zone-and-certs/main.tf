# Este bloque configura el proveedor de AWS con un alias llamado "virginia" para la región us-east-1 y el perfil
# "crossfitapp".
provider "aws" {
  alias = "virginia"
  region = "us-east-1"
  profile = "default"
}
#  Este bloque recupera información sobre la zona de Route 53 asociada al dominio ${var.project_name}.tfm.eu.
data "aws_route53_zone" "main" {
  name = "${var.project_name}.tfm.eu"
  tags = {
    Name = "${var.project_name}.tfm.eu"
  }
}
# Estos bloques crean certificados ACM en las regiones us-east-1 y eu-west-3 para el dominio principal y subdominios
# respectivamente. Se especifica el método de validación DNS y se proporcionan etiquetas para identificación.
resource "aws_acm_certificate" "us-east-1" {
  domain_name       = data.aws_route53_zone.main.name
  validation_method = "DNS"
  provider          = aws.virginia

  tags = {
    Name = data.aws_route53_zone.main.name
  }
}

resource "aws_acm_certificate" "us-west-1" {
  domain_name               = "*.${data.aws_route53_zone.main.name}"
  validation_method         = "DNS"
  subject_alternative_names = [data.aws_route53_zone.main.name]

  tags = {
    Name = data.aws_route53_zone.main.name
  }
}
#  Estos bloques crean registros en Route 53 necesarios para la validación del certificado ACM en cada región. Se
# crean registros para cada opción de validación de dominio (DVO) proporcionada por ACM,
# utilizando los nombres y valores de los registros proporcionados por ACM.
resource "aws_route53_record" "us-east-1_certificate_record" {
  for_each = {
    for dvo in aws_acm_certificate.us-east-1.domain_validation_options : dvo.domain_name => {
      name    = dvo.resource_record_name
      record  = dvo.resource_record_value
      type    = dvo.resource_record_type
      zone_id = data.aws_route53_zone.main.zone_id
    }
  }

  allow_overwrite = true
  name            = each.value.name
  records         = [each.value.record]
  ttl             = 60
  type            = each.value.type
  zone_id         = each.value.zone_id
}
# Estos bloques inician la validación de los certificados ACM en cada región. Se especifica el ARN del certificado y
# los registros FQDN de validación para completar el proceso de validación del certificado en Route 53.
resource "aws_acm_certificate_validation" "us-east-1_certificate_validation" {
  certificate_arn         = aws_acm_certificate.us-east-1.arn
  validation_record_fqdns = [for record in aws_route53_record.us-east-1_certificate_record : record.fqdn]
  provider = aws.virginia
}

resource "aws_route53_record" "us-west-1_certificate_record" {
  for_each = {
    for dvo in aws_acm_certificate.us-west-1.domain_validation_options : dvo.domain_name => {
      name    = dvo.resource_record_name
      record  = dvo.resource_record_value
      type    = dvo.resource_record_type
      zone_id = data.aws_route53_zone.main.zone_id
    }
  }

  allow_overwrite = true
  name            = each.value.name
  records         = [each.value.record]
  ttl             = 60
  type            = each.value.type
  zone_id         = each.value.zone_id
}

resource "aws_acm_certificate_validation" "us-west-1_certificate_validation" {
  certificate_arn         = aws_acm_certificate.us-west-1.arn
  validation_record_fqdns = [for record in aws_route53_record.us-west-1_certificate_record : record.fqdn]
}