# ============================
# Etapa 1: Construcción
# ============================
FROM eclipse-temurin:25-jdk-jammy AS builder

WORKDIR /app

# Copiar Maven Wrapper y config
COPY mvnw pom.xml ./
COPY .mvn .mvn

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline --batch-mode

# Copiar código fuente
COPY src src

# Construir el JAR (sin tests)
RUN ./mvnw clean package -DskipTests --batch-mode

# ============================
# Etapa 2: Producción
# ============================
FROM eclipse-temurin:25-jre-jammy

WORKDIR /app

# Copiar directamente el JAR generado
COPY --from=builder /app/target/*.jar app.jar

# Puerto por defecto de Spring Boot
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]