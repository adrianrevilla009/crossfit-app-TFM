output "hosted_zone_id" {
  value = data.aws_route53_zone.main.zone_id
}

output "cloudfront_certificate_arn" {
  value = aws_acm_certificate.us-east-1.arn
}

output "aws_acm_certificate_arn" {
  value = aws_acm_certificate.us-west-1.arn
}
