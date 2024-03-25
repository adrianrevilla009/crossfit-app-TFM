# Este bloque define la creación de una VPC en AWS. Se especifica el bloque CIDR (Claseless Inter-Domain Routing)
# para la VPC y si se habilita o no la resolución de nombres DNS.
 resource "aws_vpc" "main" {
  cidr_block           = var.vpc_cidr
  enable_dns_hostnames = true
  
  tags = {
    Name = "${var.vpc_name}-vpc"
    "kubernetes.io/cluster/${var.cluster_name}" = "shared"
  }
}
# Este bloque define la creación de subredes en la VPC. Puedes ver dos recursos aws_subnet:
# uno para subredes públicas y otro para subredes privadas. Para cada subred, se especifica
# la VPC a la que pertenecerá, el bloque CIDR de la subred, la zona de disponibilidad en
# la que se creará y si se asignará una IP pública a las instancias en la subred.
resource "aws_subnet" "public" {
  vpc_id                   = aws_vpc.main.id
  cidr_block               = element(var.public_subnets_cidr, count.index)
  availability_zone        = element(var.availability_zones_public, count.index)
  count                    = length(var.public_subnets_cidr)
  map_public_ip_on_launch  = true
  depends_on = [ aws_vpc.main ]
  
  tags = {
      "kubernetes.io/cluster/${var.cluster_name}" = "shared"
      "kubernetes.io/role/elb" = 1
    Name   = "node-group-subnet-${count.index + 1}"
    state  = "public"
  }
}



resource "aws_subnet" "private" {
  vpc_id                   = aws_vpc.main.id
  cidr_block               = element(var.private_subnets_cidr, count.index)
  availability_zone        = element(var.availability_zones_private, count.index)
  count                    = length(var.private_subnets_cidr)
  depends_on = [ aws_vpc.main ]
  
  tags = {

        "kubernetes.io/cluster/${var.cluster_name}" = "shared"
        "kubernetes.io/role/internal-elb" = 1
      "Name"   = "worker-subnet-${count.index + 1}"
    "state"  = "private"
  }
}
# Este bloque define la creación de una puerta de enlace de internet para la VPC. La puerta de enlace
# de internet permite que las instancias dentro de la VPC se comuniquen con Internet.
resource "aws_internet_gateway" "gw" {
  vpc_id = aws_vpc.main.id
  depends_on = [ aws_vpc.main ]

  tags = {
    Name = "eks-internet-gateway"
  }
}
# Este bloque define la creación de direcciones IP elásticas (Elastic IP, EIP) para la puerta de enlace NAT.
# Las direcciones IP elásticas se pueden asociar a instancias EC2 y permiten que las
# instancias mantengan la misma dirección IP, incluso si se detienen y vuelven a iniciar.
resource "aws_eip" "nat" {
  vpc              = true
  count = length(var.private_subnets_cidr)
  public_ipv4_pool = "amazon"
}
# Este bloque define la creación de una puerta de enlace NAT en la VPC. La puerta de enlace NAT
# permite que las instancias dentro de las subredes privadas se comuniquen con Internet,
# pero no permite que Internet inicie conexiones con las instancias.
resource "aws_nat_gateway" "gw" {
  count         = length(var.private_subnets_cidr)
  allocation_id = element(aws_eip.nat.*.id, count.index)
  subnet_id     = element(aws_subnet.public.*.id, count.index)
  depends_on    = [aws_internet_gateway.gw]

  tags = {
    Name = "eks-nat_Gateway-${count.index + 1}"
  }
}
# Este bloque define la creación de tablas de enrutamiento en la VPC. Se especifica la VPC a
# la que pertenecerá la tabla de enrutamiento y se define una o más rutas de destino,
# como rutas a la puerta de enlace de internet o a la puerta de enlace NAT.
resource "aws_route_table" "internet-route" {
  vpc_id = aws_vpc.main.id
  route {
    cidr_block = "${var.cidr_block-internet_gw}"
    gateway_id = aws_internet_gateway.gw.id
  }
  depends_on = [ aws_vpc.main ]
  tags  = {
      Name = "eks-public_route_table"
      state = "public"
  }
}

  resource "aws_route_table" "nat-route" {
  vpc_id = aws_vpc.main.id
  count  = length(var.private_subnets_cidr)
  route {
    cidr_block = "${var.cidr_block-nat_gw}"
    nat_gateway_id = element(aws_nat_gateway.gw.*.id, count.index)
  }
  depends_on = [ aws_vpc.main ]
  tags  = {
      Name = "eks-nat_route_table-${count.index + 1}"
      state = "public"
  } 
}
#  Este bloque define la asociación entre subredes y tablas de enrutamiento. Se especifica la
# subred y la tabla de enrutamiento a asociar. Esto determina cómo se enrutan los paquetes de red
# para las instancias en la subred.
resource "aws_route_table_association" "public" {
  count          = length(var.public_subnets_cidr)
  subnet_id      = element(aws_subnet.public.*.id, count.index)
  route_table_id = aws_route_table.internet-route.id
  
  depends_on = [ aws_route_table.internet-route ,
                 aws_subnet.public
  ]
}


resource "aws_route_table_association" "private" {
  count          = length(var.private_subnets_cidr)
  subnet_id      = element(aws_subnet.private.*.id, count.index)
  route_table_id = element(aws_route_table.nat-route.*.id, count.index)
  depends_on = [ aws_route_table.nat-route ,
                 aws_subnet.private
  ]
}


