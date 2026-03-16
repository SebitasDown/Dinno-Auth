# Etapa de construcción
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
# Copiar solo el pom.xml primero para aprovechar la caché de dependencias
COPY pom.xml .
# Descargar dependencias (esto se cacheará si el pom.xml no cambia)
RUN mvn dependency:go-offline -B
# Copiar el código fuente
COPY src ./src
# Compilar y empaquetar
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Copiar el JAR desde la etapa de construcción
# Usamos un nombre específico o wildcard, asegurándonos de que sea el JAR correcto
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]