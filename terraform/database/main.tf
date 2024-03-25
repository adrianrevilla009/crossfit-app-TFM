# Este bloque define la creación de un grupo de subredes de base de datos en AWS.Se especifica un nombre
# para el grupo y las IDs de las subredes privadas en las que se desplegarán las instancias de base de datos.
# También se definen etiquetas para el grupo.
resource "aws_db_subnet_group" "Groups" {
  name       = "db groups"
  subnet_ids = var.private_subnets

  tags = {
    Name = "DB subnet group"
  }
}
# Este bloque define la creación de un grupo de seguridad en AWS. Se especifica un nombre y una descripción para el
# grupo, así como el ID de la VPC en la que se aplicará el grupo. También se definen etiquetas para el grupo.
resource "aws_security_group" "data" {
  name        = "data-SG"
  description = "Allow SQL inbound traffic"
  vpc_id      = var.vpc_id

  tags = {
    Name = "data_server-SG"
  }

}
# Este bloque define una regla de seguridad para el grupo de seguridad creado anteriormente. La regla permite
# el tráfico de entrada en el puerto 5432 (puerto por defecto de PostgreSQL) desde las direcciones IP especificadas
# en var.vpc_cidr.
resource "aws_security_group_rule" "data" {
  type              = "ingress"
  from_port         = 5432
  to_port           = 5432
  protocol          = "tcp"
  cidr_blocks       = [var.vpc_cidr]
  security_group_id = aws_security_group.data.id

  depends_on = [aws_security_group.data]
}
# Este bloque define la creación de una instancia de base de datos en AWS. Se especifican diversos parámetros,
# como el identificador de la instancia, el tamaño de almacenamiento asignado, el tipo de almacenamiento,
# el motor de base de datos, la versión del motor, la clase de instancia, el nombre de la base de datos,
# si es accesible públicamente, el grupo de subredes de base de datos, los IDs de los grupos de seguridad aplicados,
# el nombre de usuario y la contraseña para acceder a la base de datos, y si se debe omitir la creación de un snapshot
# final al eliminar la instancia.
resource "aws_db_instance" "db" {
  identifier             = "${var.identifier}"
  allocated_storage      = var.allocated_storage
  storage_type           = var.storage_type
  engine                 = var.engine
  engine_version         = var.engine_version
  instance_class         = var.instance_class
  db_name                = var.database_name
  publicly_accessible    = false
  db_subnet_group_name   = aws_db_subnet_group.Groups.name
  vpc_security_group_ids = [aws_security_group.data.id]
  username               = var.secret_id
  password               = var.secret_pass
  skip_final_snapshot    = true

  depends_on = [aws_db_subnet_group.Groups, aws_security_group.data]

}

