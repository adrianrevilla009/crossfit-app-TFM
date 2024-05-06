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

namespace_monitor = monitorizacion
if ($env:NAMESPACE -ne $null) {
    namespace_monitor = $env:NAMESPACE
}

namespace_cd = argocd
if ($env:NAMESPACE -ne $null) {
    namespace_cd = $env:NAMESPACE
}

namespace_testing = testing
if ($env:NAMESPACE -ne $null) {
    namespace_testing = $env:NAMESPACE
}

Write-Host "Estas son las variables de entorno definidas:
Path de helm -> $helm_path,
Cpus de minikube -> $minikube_cpus,
Memoria de minikube -> $minikube_memory,
Namespace -> $namespace,
Namespace monitorizacion -> $namespace_monitor,
Namespace CD -> $namespace_cd,
Namespace testing -> $namespace_testing,
Nombre del chart de la app -> $helm_chart"

minikube start --cpus $minikube_cpus --memory $minikube_memory --driver=docker
# --network-plugin=cni --cni=false --diver=hyperv
minikube addons enable ingress


# APP
kubectl create namespace $namespace

helm install $helm_chart &helm_path --namespace $namespace --set apigateway.microservice.version=$(cat version.txt) --set classes.microservice.version=$(cat version.txt) --set documents.microservice.version=$(cat version.txt) --set security.microservice.version=$(cat version.txt)

kubectl -n $namespace port-forward svc/api-gateway-microservice 8080:8083

# MONITOR
kubectl create namespace $namespace_monitor

helm repo add grafana https://grafana.github.io/helm-charts
helm repo update
helm upgrade --install loki --namespace=$namespace_monitor --values .\helm\monitor\grafana-values.yaml grafana/loki-stack

kubectl port-forward svc/loki-grafana --namespace $namespace_monitor 3000:80

kubectl get secret --namespace $namespace_monitor loki-grafana -o jsonpath="{.data.admin-password}" | ForEach-Object { [System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($_)) }

kubectl -n $namespace_monitor port-forward svc/loki-prometheus-server 9000:80

# ARGO
helm repo add argo https://argoproj.github.io/argo-helm

helm repo update

kubectl create namespace $namespace_cd

helm install argocd argo/argo-cd --namespace $namespace_cd

kubectl apply -f . -R --namespace $namespace_cd

kubectl port-forward svc/argocd-server -n $namespace_cd 8090:443

kubectl -n $namespace_cd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | ForEach-Object { [System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($_)) }

# TESTING
# artillery run .\test\load-test.yaml

kubectl create namespace $namespace_testing

helm install jmeter .\helm\testing\distributed-jmeter\  -n $namespace_testing

kubectl cp .\test\jmeter\jmeter-get-classes-test.jmx jmeter-distributed-jmeter-master-578cc6544b-s87zt:/jmeter -n $namespace_testing

kubectl get pods -l app.kubernetes.io/component=server -n $namespace_testing -o jsonpath='{.items[*].status.podIP}'

kubectl exec -it jmeter-distributed-jmeter-master-578cc6544b-s87zt -n $namespace_testing -- jmeter -n -t /jmeter/jmeter-get-classes-test.jmx -R 10.244.0.52,10.244.0.55,10.244.0.54

# SECURIZACION
kubectl apply -f .\helm\networking\ingress-api-gateway-microservice.yaml




