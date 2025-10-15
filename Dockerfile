# Stage 1: Build con Maven
FROM maven:3.9.3-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom.xml y src completos
COPY pom.xml .
COPY src ./src

# Generar jar
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copiar solo el jar desde el stage de build
COPY --from=build /app/target/*.jar app.jar

# Exponer puerto
EXPOSE 8080

# Comando para correr la app
CMD ["java", "-jar", "app.jar"]
