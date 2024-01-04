FROM maven:3.9.0-eclipse-temurin-17 as builder
WORKDIR /crossfit-app-TFM/classes-microservice
COPY /src /crossfit-app-TFM/classes-microservice/src
COPY pom.xml /crossfit-app-TFM/classes-microservice
COPY settings.xml /root/.m2/settings.xml
RUN mvn -B clean package -DskipTests

# Imagen base para el contenedor de la aplicación
FROM eclipse-temurin:17-jdk
WORKDIR /usr/src/app/
COPY --from=builder /crossfit-app-TFM/classes-microservice/target/*.jar /usr/src/app/

EXPOSE 8080
CMD [ "java", "-jar", "classes-microservice-1.0.0.jar" ]