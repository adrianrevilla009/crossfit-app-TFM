# Crossfit box app
Adrian Revilla

## Proyecto
El proyecto consiste en desarrollar una aplicación que engloba varias fases del ciclo de vida del software, como es el diseño, el desarrollo en sí, el testing, el despliegue y el mantenimiento.
La temática data de un gestor de reservas de una aplicación para un box de crossfit, en la cual el usuario podrá registrarse, identificarse, reservar clases, consultar clases y descargar la memoria de su historial.

El objetivo principal es definir una aplicación funcional donde el usuario pueda operar a su antojo, realizando varias operaciones contra el backend que va a componer la aplicación.

Se desea cumplir con los siguientes objetivos a nivel técnico.
- Arquitectura funcional por entornos (desarrollo y producción)
- Sistema de 4 microservicios desarrollados en Java
- Utilización de arquitecturas hexagonales y MVC en diferentes microservicios
- Securizar los microservicios mediante una caché Redis
- Comunicación asíncrona con un broker de mensajería Kafka
- Utilización de bases de datos relacionales MySQL y no relacionales MongoDB en base a la necesidad
- Persistencia de ficheros en un volumen de persistencia
- Uso de un gateway como proxy para el trackeo de todas las peticiones
- Sistema de monitorización para el almacenamiento de logs con Loki
- Sistema de monitorización para la visualización de métricas con Grafana
- Desarrollo de una librería común en Github Artifact
- Utilización de monorepo github para el almacenamiento del proyecto
- Integración continua y versionado con Github Actions
- Despliegue continuo con argoCD
- Desarrollo de un cluster local Kubernetes para el despliegue con Minikube
- Versionado de manifiestos Kubernetes mediante Helm
- Artillery como herramienta de test de carga
- Script de ejecución inicial de todo el cluster
- Terraform como IAC para el despliegue en un proveedor cloud
- AWS como proveedor cloud para el despliegue en el entorno de producción
- Analizador de código con SonarCloud para detectar code smells

## Arquitectura
![](C:\Users\adria\Desktop\micros.png)

## Ejecucion
Para desplegar el stack se han de seguir los siguientes pasos:

### Actualizar el sistema
    echo "Iniciando el script de configuración de EC2 Minikube"

    echo "Actualizando el sistema..."
    sudo apt-get update -y

    echo "Instalando paquetes necesarios..."
    sudo apt-get install -y curl vim git
### Instalar docker
    echo "Instalando Docker..."
    curl -fsSL https://get.docker.com -o get-docker.sh
    sh get-docker.sh

    sudo usermod -aG docker ubuntu && newgrp docker
### Instalar minikube
    echo "Instalando Minikube..."
    curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
    chmod +x minikube
    sudo mv minikube /usr/local/bin/

    echo "Instalando kubectl..."
    curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
    chmod +x kubectl
    sudo mv kubectl /usr/local/bin/kubectl
### Instalar helm
    echo "Instalando Helm..."
    curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
### Instalar git y descargar el proyecto
    echo "Instalando git..."
    sudo apt install git

    echo "Descargando proyecto de GitHub..."
    sudo mkdir -p /app/
    git config --global user.name adrianrevilla009
    git config --global user.email adrianrg1996@gmail.com
    sudo git clone https://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@github.com/adrianrevilla009/crossfit-app-TFM.git /app/
    cd /app/
### Iniciar minikube
    echo "Iniciando Minikube..."
    minikube start --cpus 4 --memory 12g --disk-size 54g --driver=docker
    minikube addons enable ingress
### Iniciar aplicacion
    echo "Instalando aplicación..."
    kubectl create namespace development
    helm install production-cluster ./helm/app --namespace development \
    --set apigateway.microservice.version=$(cat version.txt) \
    --set classes.microservice.version=$(cat version.txt) \
    --set documents.microservice.version=$(cat version.txt) \
    --set security.microservice.version=$(cat version.txt)
### Instalar monitoreo
     echo "Instalando monitoreo..."
     kubectl create namespace monitoring

     helm repo add grafana https://grafana.github.io/helm-charts
     helm repo update
     helm upgrade --install loki --namespace=monitoring --values helm/monitor/grafana-values.yaml grafana/loki-stack
 
     echo "Obteniendo contraseña del administrador de Grafana..."
     kubectl get secret --namespace monitoring loki-grafana -o jsonpath="{.data.admin-password}" | ForEach-Object { [System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($_)) }
### Instalar argoCD
     echo "Instalando argoCD..."
     kubectl create namespace argocd
     helm repo add argo https://argoproj.github.io/argo-helm
     helm repo update
     helm install argocd argo/argo-cd --namespace argocd
     kubectl apply -f . -R --namespace argocd
 
     echo "Obteniendo contraseña inicial de argocd..."
     kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | ForEach-Object { [System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($_)) }
### Instalar jmeter
     echo "Instalando testing..."
     kubectl create namespace testing
     helm install jmeter helm/testing/distributed-jmeter/  -n testing
     kubectl cp test/jmeter/jmeter-get-classes-test.jmx jmeter-distributed-jmeter-master-578cc6544b-s87zt:/jmeter -n testing
     kubectl get pods -l app.kubernetes.io/component=server -n testing -o jsonpath='{.items[*].status.podIP}'
### Exponer los servicios
    echo "Servicios expuestos"
    kubectl -n development port-forward svc/api-gateway-microservice 8080:8083
    kubectl port-forward svc/loki-grafana --namespace monitoring 3000:80
    kubectl -n monitoring port-forward svc/loki-prometheus-server 9000:80
    kubectl port-forward svc/argocd-server -n argocd 8090:443

    echo "Script de configuración de EC2 Minikube completado con éxito"

