# ============================
# Etapa 1: Construcción
# ============================
FROM eclipse-temurin:21-jdk-jammy AS builder

WORKDIR /app

# Copiar Maven Wrapper y configuración
COPY mvnw mvnw.cmd pom.xml ./
COPY .mvn/ .mvn/

# Dar permisos al wrapper
RUN chmod +x mvnw

# 🔍 Verificar que los archivos del wrapper se copiaron correctamente
RUN ls -R .mvn && cat .mvn/wrapper/maven-wrapper.properties

# Descargar dependencias sin compilar (modo offline)
RUN ./mvnw dependency:go-offline --batch-mode

# Copiar código fuente
COPY src src

# Construir el JAR (sin ejecutar tests)
RUN ./mvnw clean package -DskipTests --batch-mode

# ============================
# Etapa 2: Producción
# ============================
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copiar el JAR generado desde la etapa anterior
COPY --from=builder /app/target/*.jar app.jar

# Puerto por defecto de Spring Boot
EXPOSE 8080

# Comando de inicio del contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]