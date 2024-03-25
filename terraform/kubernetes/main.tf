# Define los proveedores de Terraform necesarios para este proyecto, especificando las versiones
# requeridas de kubectl y helm.
terraform {
  required_providers {
    kubectl = {
      source  = "gavinbunney/kubectl"
      version = "1.14.0"
    }
    helm = {
      source  = "hashicorp/helm"
      version = ">= 2.8.0"
    }
  }
}
# Recopila información sobre el clúster EKS existente, como su nombre,
# para su posterior uso en la configuración de Kubernetes.
data "aws_eks_cluster" "cluster" {
  name = var.cluster_id
}
# Obtiene la información de autenticación necesaria para acceder al clúster EKS, como el token de autenticación.
data "aws_eks_cluster_auth" "cluster" {
  name = var.cluster_id
}
# Recopila información sobre la identidad del usuario que ejecuta la configuración de Terraform.
data "aws_caller_identity" "current" {}
# Configura el proveedor kubectl para interactuar con el clúster EKS. Se especifica la dirección del clúster,
# el certificado de CA y el token de autenticación.
provider "kubectl" {
  host                   = data.aws_eks_cluster.cluster.endpoint
  cluster_ca_certificate = base64decode(data.aws_eks_cluster.cluster.certificate_authority[0].data)
  token                  = data.aws_eks_cluster_auth.cluster.token
  load_config_file       = false
}
#  Configura el proveedor helm para gestionar paquetes Helm en el clúster EKS. Se especifican detalles similares al
# proveedor kubectl, incluido el uso de AWS CLI para obtener un token de autenticación.
provider "helm" {
  kubernetes {
    host                   = data.aws_eks_cluster.cluster.endpoint
    cluster_ca_certificate = base64decode(data.aws_eks_cluster.cluster.certificate_authority[0].data)
    exec {
      api_version = "client.authentication.k8s.io/v1beta1"
      args        = ["eks", "get-token", "--region", "${var.zone}", "--profile", "${var.cluster_name}", "--cluster-name", data.aws_eks_cluster.cluster.name]
      command     = "aws"
    }
  }
}
# Estos bloques recopilan archivos YAML locales que contienen la configuración de varios recursos de Kubernetes,
# como ArgoCD, Cert Manager y OpenTelemetry Operator.
data "kubectl_file_documents" "argocd" {
  content = file("./kubernetes/manifests/argocd/install.yaml")
}

data "kubectl_file_documents" "cert_manager" {
  content = file("./kubernetes/manifests/cert-manager/cert-manager.yaml")
}

data "kubectl_file_documents" "opentelemetry-operator" {
  content = file("./kubernetes/manifests/opentelemetry-operator/opentelemetry-operator.yaml")
}
# Estos bloques crean espacios de nombres en el clúster EKS para diferentes componentes,
# como ArgoCD, Cert Manager y OpenTelemetry Operator.
resource "kubernetes_namespace" "argocd_namespace" {
  metadata {
    name = "argocd"
  }
}

resource "kubernetes_namespace" "cert_manager_namespace" {
  metadata {
    name = "cert-manager"
  }
}

resource "kubernetes_namespace" "opentelemetry_operator_system_namespace" {
  metadata {
    labels = {
      "app.kubernetes.io/name" = "opentelemetry-operator"
      control-plane            = "controller-manager"
    }

    name = "opentelemetry-operator-system"
  }
}

resource "kubernetes_namespace" "ingress-nginx_namespace" {
  metadata {
    name = "ingress-nginx"
  }
}
# Estos bloques despliegan recursos en el clúster EKS utilizando archivos YAML locales. Se utilizan para desplegar
# recursos como ArgoCD, Cert Manager y OpenTelemetry Operator.
resource "kubectl_manifest" "argocd" {
  for_each           = data.kubectl_file_documents.argocd.manifests
  yaml_body          = each.value
  depends_on         = [kubernetes_namespace.argocd_namespace]
  override_namespace = kubernetes_namespace.argocd_namespace.metadata[0].name
}

resource "kubectl_manifest" "cert_manager" {
  for_each   = data.kubectl_file_documents.cert_manager.manifests
  yaml_body  = each.value
  depends_on = [kubernetes_namespace.cert_manager_namespace]
}

resource "kubectl_manifest" "opentelemetry-operator" {
  for_each  = data.kubectl_file_documents.opentelemetry-operator.manifests
  yaml_body = each.value
  depends_on = [
    kubernetes_namespace.cert_manager_namespace,
    kubectl_manifest.cert_manager
  ]
}
# Este bloque despliega un paquete Helm en el clúster EKS. En este caso, despliega el controlador Ingress-Nginx.
resource "helm_release" "ingress-nginx_release" {
  name       = "ingress-nginx"
  repository = "https://kubernetes.github.io/ingress-nginx"
  chart      = "ingress-nginx"
  version    = "4.4.2"
  namespace  = kubernetes_namespace.ingress-nginx_namespace.metadata[0].name

  values = [
    "${file("./kubernetes/manifests/ingress-nginx/values.yaml")}"
  ]

  set {
    name  = "controller.service.annotations.service\\.beta\\.kubernetes\\.io/aws-load-balancer-ssl-cert"
    value = var.load_balancer_certificate_arn
  }
}

provider "kubernetes" {
  host                   = data.aws_eks_cluster.cluster.endpoint
  cluster_ca_certificate = base64decode(data.aws_eks_cluster.cluster.certificate_authority[0].data)
  token                  = data.aws_eks_cluster_auth.cluster.token
}
# Este bloque crea un servicio en el clúster EKS que apunta a una instancia de base de datos externa.
resource "kubernetes_service" "db_instance_external_name" {
  metadata {
    name = "${var.cluster_name}-db"
  }
  spec {
    type          = "ExternalName"
    external_name = var.db_endpoint
  }
}
# Estos bloques crean secretos en el clúster EKS para almacenar credenciales sensibles, como credenciales de base de
# datos y tokens de acceso a repositorios de GitHub.
resource "kubernetes_secret" "db_secret" {
  metadata {
    name = "${var.cluster_name}-db-secret"

    labels = {
      app     = "${var.cluster_name}-db"
      project = "${var.cluster_name}"
    }
  }
  data = {
    username = var.db_secret_id
    password = var.db_secret_pass
  }
}

resource "kubernetes_secret" "argo_github_repository_secret" {
  metadata {
    name      = "${var.cluster_name}-repo-config"
    namespace = "argocd"
    labels = {
      "argocd.argoproj.io/secret-type" = "repository"
    }
    annotations = {
      "managed-by" = "argocd.argoproj.io"
    }
  }

  binary_data = {
    username = base64encode("${var.github_username}")
    password = base64encode("${var.github_token}")
    project  = base64encode("default")
    type     = base64encode("git")
    url      = base64encode("${var.github_repository_url}")
  }

  type = "Opaque"
}

data "aws_iam_user" "tempo_user" {
  user_name = "${var.cluster_name}-tempo-user"
}

resource "aws_iam_access_key" "tempo_access_key" {
  user = data.aws_iam_user.tempo_user.user_name
}

resource "kubernetes_secret" "tempo_bucket_secret" {
  metadata {
    name      = "tempo-s3"
    namespace = "default"
  }

  binary_data = {
    access_key = base64encode("${aws_iam_access_key.tempo_access_key.id}")
    secret_key = base64encode("${aws_iam_access_key.tempo_access_key.secret}")
  }

  type = "Opaque"
}

data "aws_iam_user" "screenshot_signer_user" {
  user_name = "${var.cluster_name}-screenshot-signer"
}

resource "aws_iam_access_key" "screenshot_signer_access_key" {
  user = data.aws_iam_user.screenshot_signer_user.user_name
}

resource "kubernetes_secret" "screenshot_signer_secret" {
  metadata {
    name      = "${var.cluster_name}-screenshot-signer-secret"
    namespace = "default"
  }

  binary_data = {
    access_key = base64encode("${aws_iam_access_key.screenshot_signer_access_key.id}")
    secret_key = base64encode("${aws_iam_access_key.screenshot_signer_access_key.secret}")
  }

  type = "Opaque"
}
# Este bloque recopila archivos YAML locales que contienen la configuración de aplicaciones ArgoCD.
data "kubectl_path_documents" "argocd_applications" {
  pattern = "../k8s/argocd/applications/*.yaml"
}

resource "kubectl_manifest" "argocd_applications" {
  for_each  = toset(data.kubectl_path_documents.argocd_applications.documents)
  yaml_body = each.value
  depends_on = [
    kubectl_manifest.opentelemetry-operator,
    kubectl_manifest.argocd,
    kubernetes_secret.argo_github_repository_secret
  ]
}

data "kubernetes_service" "nginx_ingress_controller" {
  metadata {
    name      = "ingress-nginx-controller"
    namespace = "ingress-nginx"
  }

  depends_on = [
    helm_release.ingress-nginx_release
  ]
}

locals {
  ingresses = ["api", "collector", "grafana", "argocd"]
}

# Este bloque crea registros en Route 53 para cada servicio ingresado en la lista local.ingresses.
# Los registros apuntan a la dirección IP del controlador Ingress-Nginx.
resource "aws_route53_record" "k8s_ingress_route53_record" {
  for_each = toset(local.ingresses)
  zone_id  = var.hosted_zone_id
  name     = "${each.key}.${var.project_name}.tfm.eu"
  type     = "CNAME"
  ttl      = "300"
  records  = [data.kubernetes_service.nginx_ingress_controller.status.0.load_balancer.0.ingress.0.hostname]

  depends_on = [
    helm_release.ingress-nginx_release
  ]
}
