# Usar OpenJDK 17
FROM openjdk:17-jdk-slim

# Crear carpeta de trabajo dentro del contenedor
WORKDIR /app

# Copiar solo los archivos necesarios
COPY . .

# Dar permisos al Maven Wrapper y construir la app
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Exponer puerto (Render lo sobreescribe con PORT)
EXPOSE 8080

# Comando para arrancar la app
CMD ["sh", "-c", "java -jar target/*.jar"]
