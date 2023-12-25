FROM maven:3.9.0-eclipse-temurin-17 as builder
WORKDIR /crossfit-app-TFM/security-microservice
COPY /src /crossfit-app-TFM/security-microservice/src
COPY pom.xml /crossfit-app-TFM/security-microservice
RUN mvn -B clean package -DskipTests

# Imagen base para el contenedor de la aplicación
FROM eclipse-temurin:17-jdk
WORKDIR /usr/src/app/
COPY --from=builder /crossfit-app-TFM/security-microservice/target/*.jar /usr/src/app/

EXPOSE 8082
CMD [ "java", "-jar", "security-microservice-1.0.0.jar" ]