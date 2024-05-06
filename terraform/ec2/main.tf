variable "instance_type" {
  type    = string
  default = "t2.xlarge"
}

variable "key_name" {
  type = string
  description = "EC2minikube"
}

resource "aws_instance" "ec2" {
  ami           = var.ami
  instance_type = var.instance_type
  key_name      = var.key_name

  # Asocia la instancia a la VPC por defecto si no se especifica una
  subnet_id = var.subnet_id
  # Asocia un security group con los puertos ssh y http abiertos para el trafico entrante
  vpc_security_group_ids = [aws_security_group.ec2_sg.id]

  root_block_device {
    volume_size           = 30
    volume_type           = "gp2"
    delete_on_termination = true
  }

  tags = {
    Name = "EC2 Minikube"
  }

#  user_data = <<-EOF
#    #!/bin/bash
#
#    echo "Iniciando el script de configuración de EC2 Minikube"
#
#    echo "Actualizando el sistema..."
#    sudo apt-get update -y
#
#    echo "Instalando paquetes necesarios..."
#    sudo apt-get install -y curl vim git
#
#    echo "Instalando Docker..."
#    curl -fsSL https://get.docker.com -o get-docker.sh
#    sh get-docker.sh
#
#    sudo usermod -aG docker ubuntu && newgrp docker
#
#    echo "Instalando Minikube..."
#    curl -Lo minikube https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
#    chmod +x minikube
#    sudo mv minikube /usr/local/bin/
#
#    echo "Instalando kubectl..."
#    curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
#    chmod +x kubectl
#    sudo mv kubectl /usr/local/bin/kubectl
#
#    echo "Instalando Helm..."
#    curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash
#
#    echo "Instalando git..."
#    sudo apt install git
#
#    echo "Descargando proyecto de GitHub..."
#    sudo mkdir -p /app/
#    git config --global user.name adrianrevilla009
#    git config --global user.email adrianrg1996@gmail.com
#    sudo git clone https://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@github.com/adrianrevilla009/crossfit-app-TFM.git /app/
#    cd /app/
#
#    echo "Iniciando Minikube..."
#    minikube start --cpus 4 --memory 12g --disk-size 54g --driver=docker --force
#    minikube addons enable ingress
#
#    echo "Instalando aplicación..."
#    kubectl create namespace development
#    helm install production-cluster ./helm/app --namespace development \
#    --set apigateway.microservice.version=$(cat version.txt) \
#    --set classes.microservice.version=$(cat version.txt) \
#    --set documents.microservice.version=$(cat version.txt) \
#    --set security.microservice.version=$(cat version.txt)
#
#    echo "Instalando monitoreo..."
#    kubectl create namespace monitoring
#
#    helm repo add grafana https://grafana.github.io/helm-charts
#    helm repo update
#    helm upgrade --install loki --namespace=monitoring --values helm/monitor/grafana-values.yaml grafana/loki-stack
#
#    echo "Obteniendo contraseña del administrador de Grafana..."
#    # kubectl get secret --namespace monitoring loki-grafana -o jsonpath="{.data.admin-password}" | ForEach-Object { [System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($_)) }
#
#    echo "Instalando argoCD..."
#    kubectl create namespace argocd
#    helm repo add argo https://argoproj.github.io/argo-helm
#    helm repo update
#    helm install argocd argo/argo-cd --namespace argocd
#    kubectl apply -f . -R --namespace argocd
#
#    echo "Obteniendo contraseña inicial de argocd..."
#    # kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | ForEach-Object { [System.Text.Encoding]::UTF8.GetString([System.Convert]::FromBase64String($_)) }
#
#    echo "Instalando testing..."
#    kubectl create namespace testing
#    helm install jmeter helm/testing/distributed-jmeter/  -n testing
#    kubectl cp test/jmeter/jmeter-get-classes-test.jmx jmeter-distributed-jmeter-master-578cc6544b-s87zt:/jmeter -n testing
#    # kubectl get pods -l app.kubernetes.io/component=server -n testing -o jsonpath='{.items[*].status.podIP}'
#
#    echo "Servicios expuestos"
#    # kubectl -n development port-forward svc/api-gateway-microservice 8080:8083
#    # kubectl port-forward svc/loki-grafana --namespace monitoring 3000:80
#    # kubectl -n monitoring port-forward svc/loki-prometheus-server 9000:80
#    # kubectl port-forward svc/argocd-server -n argocd 8090:443
#
#    echo "Script de configuración de EC2 Minikube completado con éxito"
#EOF
}

variable "subnet_id" {
  description = "ID de la subred donde se desplegará la instancia EC2"
  type        = string
}

resource "aws_security_group" "ec2_sg" {
  name        = "ec2_security_group"
  description = "Security group for EC2 instance"

  # Regla para permitir tráfico SSH (puerto 22) desde cualquier IP
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] # Permite tráfico desde cualquier IP
  }

  # Regla para permitir tráfico HTTP (puerto 80) desde cualquier IP
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] # Permite tráfico desde cualquier IP
  }

  ingress {
    from_port   = 8080
    to_port     = 8080
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"] # Permite tráfico desde cualquier IP
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1" # Permite el tráfico saliente (todas las IP y todos los puertos)
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = "EC2 Security Group"
  }
}
