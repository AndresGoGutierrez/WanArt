# Usar OpenJDK 17
FROM openjdk:17-jdk-slim

# Crear carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copiar Maven Wrapper y configuración
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copiar solo el código fuente
COPY src src

# Dar permisos al Maven Wrapper y construir la app
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Exponer puerto (Render lo sobreescribe con PORT)
EXPOSE 8080

# Comando para arrancar la app
CMD ["java", "-jar", "target/Sistema-Galeria-0.0.1-SNAPSHOT.jar"]
