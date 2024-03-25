output "cluster_id" {
  value = aws_eks_cluster.eks_cluster.id
}

output "cluster_name" {
  value = aws_eks_cluster.eks_cluster.name
}

output "oidc_provider_url" {
  value = aws_eks_cluster.eks_cluster.identity[0].oidc[0].issuer
}
