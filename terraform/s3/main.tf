# Este bloque define la creación de un bucket de S3 en AWS para almacenar los archivos web de la aplicación CrossFit.
# Se especifica el nombre del bucket, se habilita la destrucción forzada (para permitir la eliminación del bucket
# incluso si contiene objetos) y se definen etiquetas para el bucket.
resource "aws_s3_bucket" "crossfitapp_web_bucket" {
  bucket        = "${var.project_name}.tfm.eu"
  force_destroy = true
  tags = {
    Name = "${var.project_name}.tfm.eu"
  }
}
# ste bloque define la configuración de bloqueo de acceso público para el bucket de S3 de la aplicación CrossFit.
# Se bloquean los ACLs públicos, se permite la política pública, se ignoran los ACLs públicos y se restringen
# los buckets públicos.
resource "aws_s3_bucket_public_access_block" "crossfitapp_web_bucket_public_access_block" {
  bucket = aws_s3_bucket.crossfitapp_web_bucket.id

  block_public_acls       = true
  block_public_policy     = false
  ignore_public_acls      = true
  restrict_public_buckets = false
}
# Este bloque define una política de bucket de S3 para permitir el acceso público a los objetos del bucket.
# La política permite que cualquier persona (identificado por el asterisco) pueda realizar la acción
# "s3:GetObject" en los objetos del bucket.
resource "aws_s3_bucket_policy" "crossfitapp_web_bucket_policy" {
  bucket = aws_s3_bucket.crossfitapp_web_bucket.id
  policy = data.aws_iam_policy_document.allow_public_access_to_s3.json
}
# Este bloque define un documento de política IAM para permitir acceso público a los objetos del bucket de S3.
# La política permite la acción "s3:GetObject" en los objetos del bucket.
data "aws_iam_policy_document" "allow_public_access_to_s3" {
  statement {
    sid = "AllowPublicRead"

    principals {
      type        = "*"
      identifiers = ["*"]
    }

    actions = [
      "s3:GetObject"
    ]

    resources = [
      "${aws_s3_bucket.crossfitapp_web_bucket.arn}/*"
    ]
  }
}
# Este bloque define la configuración de versionado para el bucket de S3 de la aplicación CrossFit.
# Se habilita el versionado para el bucket.
resource "aws_s3_bucket_versioning" "crossfitapp_web_bucket_versioning" {
  bucket = aws_s3_bucket.crossfitapp_web_bucket.id
  versioning_configuration {
    status = "Enabled"
  }
}
# Este bloque define la configuración del sitio web para el bucket de S3 de la aplicación CrossFit.
# Se especifica el documento de índice y el documento de error.
resource "aws_s3_bucket_website_configuration" "crossfitapp_web_bucket_website_configuration" {
  bucket = aws_s3_bucket.crossfitapp_web_bucket.id

  index_document {
    suffix = "index.html"
  }

  error_document {
    key = "index.html"
  }
}
# Este bloque define los controles de propiedad para el bucket de S3 de la aplicación CrossFit.
# Se establece la propiedad de los objetos del bucket como "BucketOwnerEnforced".
resource "aws_s3_bucket_ownership_controls" "crossfitapp_web_bucket_ownership_controls" {
  bucket = aws_s3_bucket.crossfitapp_web_bucket.id

  rule {
    object_ownership = "BucketOwnerEnforced"
  }
}
# Este bloque define la creación de una distribución de CloudFront para el bucket de S3.
# CloudFront es un servicio de CDN (Content Delivery Network) de AWS que ayuda a distribuir contenido web de
# manera rápida y eficiente. Se especifica el origen del bucket de S3, las configuraciones de caché y
# las respuestas personalizadas para errores.
resource "aws_cloudfront_distribution" "s3_distribution" {
  origin {
    domain_name = aws_s3_bucket_website_configuration.crossfitapp_web_bucket_website_configuration.website_endpoint
    origin_id   = "${var.project_name}.tfm.eu"

    custom_origin_config {
      http_port                = 80
      https_port               = 443
      origin_protocol_policy   = "http-only"
      origin_ssl_protocols     = ["TLSv1.2"]
      origin_keepalive_timeout = 5
      origin_read_timeout      = 30
    }
  }

  enabled             = true
  is_ipv6_enabled     = true
  default_root_object = "index.html"
  aliases             = ["${var.project_name}.tfm.eu"]
  price_class         = "PriceClass_100"
  wait_for_deployment = false

  restrictions {
    geo_restriction {
      restriction_type = "none"
      locations        = []
    }
  }

  viewer_certificate {
    acm_certificate_arn      = var.cloudfront_certificate_arn
    ssl_support_method       = "sni-only"
    minimum_protocol_version = "TLSv1.2_2021"
  }

  default_cache_behavior {
    allowed_methods        = ["GET", "HEAD", "OPTIONS", "PUT", "POST", "PATCH", "DELETE"]
    cached_methods         = ["GET", "HEAD"]
    target_origin_id       = "${var.project_name}.tfm.eu"
    compress               = true
    viewer_protocol_policy = "redirect-to-https"
    min_ttl                = 0
    default_ttl            = 3600
    max_ttl                = 86400
    forwarded_values {
      query_string = false

      cookies {
        forward = "none"
      }
    }
  }

  custom_error_response {
    error_caching_min_ttl = 10
    error_code            = 400
    response_code         = 400
    response_page_path    = "/error-400"
  }
  custom_error_response {
    error_caching_min_ttl = 10
    error_code            = 403
    response_code         = 403
    response_page_path    = "/index.html"
  }
  custom_error_response {
    error_caching_min_ttl = 10
    error_code            = 404
    response_code         = 404
    response_page_path    = "/error-404"
  }
  custom_error_response {
    error_caching_min_ttl = 10
    error_code            = 405
    response_code         = 405
    response_page_path    = "/index.html"
  }
  custom_error_response {
    error_caching_min_ttl = 10
    error_code            = 414
    response_code         = 414
    response_page_path    = "/index.html"
  }
  custom_error_response {
    error_caching_min_ttl = 10
    error_code            = 416
    response_code         = 416
    response_page_path    = "/index.html"
  }
  custom_error_response {
    error_caching_min_ttl = 10
    error_code            = 500
    response_code         = 500
    response_page_path    = "/error-500"
  }
  custom_error_response {
    error_caching_min_ttl = 10
    error_code            = 501
    response_code         = 501
    response_page_path    = "/index.html"
  }
  custom_error_response {
    error_caching_min_ttl = 10
    error_code            = 502
    response_code         = 502
    response_page_path    = "/index.html"
  }
  custom_error_response {
    error_caching_min_ttl = 10
    error_code            = 503
    response_code         = 503
    response_page_path    = "/index.html"
  }
  custom_error_response {
    error_caching_min_ttl = 10
    error_code            = 504
    response_code         = 504
    response_page_path    = "/index.html"
  }
}
# Este bloque define un registro de Route 53 para la aplicación CrossFit. Se especifica el nombre del dominio y se
# establece un alias para apuntar a la distribución de CloudFront.
resource "aws_route53_record" "crossfitapp_web_bucket_route53_record" {
  zone_id = var.hosted_zone_id
  name    = "${var.project_name}.tfm.eu"
  type    = "A"

  alias {
    name                   = aws_cloudfront_distribution.s3_distribution.domain_name
    zone_id                = aws_cloudfront_distribution.s3_distribution.hosted_zone_id
    evaluate_target_health = false
  }
}
# Este bloque define la creación de un bucket de S3 para almacenamiento temporal.
# Se especifica el nombre del bucket y se habilita la destrucción forzada.
resource "aws_s3_bucket" "tempo_storage_bucket" {
  bucket        = "${var.project_name}-tempo-storage"
  force_destroy = true

  tags = {
    Name = "${var.project_name}-tempo-storage"
  }
}
# Este bloque define el control de acceso para el bucket de S3 de almacenamiento temporal.
# Se establece el control de acceso del bucket como privado.
resource "aws_s3_bucket_acl" "tempo_storage_bucket_acl" {
  bucket = aws_s3_bucket.tempo_storage_bucket.id
  acl    = "private"
}
# Este bloque define la configuración de bloqueo de acceso público para el bucket de S3 de almacenamiento temporal.
# Se bloquean los ACLs públicos, se bloquea la política pública, se ignoran los ACLs públicos y se restringen
# los buckets públicos.
resource "aws_s3_bucket_public_access_block" "tempo_storage_bucket_public_access_block" {
  bucket = aws_s3_bucket.tempo_storage_bucket.id

  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}
