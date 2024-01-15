if ($env:HELM_PATH -eq $null) {
    Write-Host "Error: Debes proporcionar el path de helm como variable de entorno."
    exit 1
}
helm_path = $env:HELM_PATH

minikube_cpus = 2
if ($env:MINIKUBE_CPUS -ne $null) {
    minikube_cpus = $env:MINIKUBE_CPUS
}

minikube_memory = 2048
if ($env:MINIKUBE_MEMORY -ne $null) {
    minikube_memory = $env:MINIKUBE_MEMORY
}

namespace = development
if ($env:NAMESPACE -ne $null) {
    namespace = $env:NAMESPACE
}

helm_chart = development-cluster
if ($env:HELM_CHART -ne $null) {
    helm_chart = $env:HELM_CHART
}

Write-Host "Estas son las variables de entorno definidas:
Path de helm -> $helm_path,
Cpus de minikube -> $minikube_cpus,
Memoria de minikube -> $minikube_memory,
Namespace -> $namespace,
Nombre del chart de la app -> $helm_chart"

minikube start --cpus $minikube_cpus --memory $minikube_memory

kubectl create namespace $namespace

helm install $helm_chart &helm_path --namespace $namespace

kubectl -n $namespace port-forward svc/api-gateway-microservice 8080:8083

minikube service grafana --namespace $namespace


