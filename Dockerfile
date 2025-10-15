# Usar JDK 17
FROM openjdk:17-jdk-slim

# Carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copiar todo el proyecto al contenedor
COPY . .

# Construir la app con Maven
RUN ./mvnw clean package -DskipTests

# Puerto que expondrá la app (Render lo sobreescribe con PORT)
EXPOSE 8080

# Comando para arrancar la app
CMD ["java", "-jar", "target/Sistema-Galeria-0.0.1-SNAPSHOT.jar"]
