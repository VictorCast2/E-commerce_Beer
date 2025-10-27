# Etapa 1: Compilar la app con Maven
FROM eclipse-temurin:21.0.3_9-jdk AS builder

WORKDIR /app

# Set environment variables for encoding
ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8

# Copiamos los archivos necesarios para descargar dependencias
COPY ./pom.xml .
COPY ./.mvn .mvn
COPY ./mvnw .

# Descargar dependencias sin compilar
RUN ./mvnw dependency:go-offline

# Copiar código fuente y compilar con encoding
COPY ./src ./src
RUN ./mvnw clean install -DskipTests -Dproject.build.sourceEncoding=UTF-8

# Etapa 2: Imagen final con solo el .jar
FROM eclipse-temurin:21.0.3_9-jre

WORKDIR /app

# Set encoding for runtime
ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8

COPY --from=builder /app/target/*.jar app.jar

# Puerto por defecto de Spring Boot
EXPOSE 8080

# Comando de inicio del contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]