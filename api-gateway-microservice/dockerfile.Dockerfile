FROM maven:3.9.0-eclipse-temurin-17 as builder
WORKDIR /crossfit-app-TFM/api-gateway-microservice
COPY /src /crossfit-app-TFM/api-gateway-microservice/src
COPY pom.xml /crossfit-app-TFM/api-gateway-microservice
RUN mvn -B clean package -DskipTests

# Imagen base para el contenedor de la aplicación
FROM eclipse-temurin:17-jdk
WORKDIR /usr/src/app/
COPY --from=builder /crossfit-app-TFM/api-gateway-microservice/target/*.jar /usr/src/app/

EXPOSE 8083
CMD [ "java", "-jar", "api-gateway-microservice-1.0.0.jar" ]