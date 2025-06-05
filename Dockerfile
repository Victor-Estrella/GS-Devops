# Etapa 1: build da aplicação
FROM maven:3.9-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: aplicação pronta
FROM eclipse-temurin:17-jdk-alpine
RUN adduser -D -H appuser
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
RUN chown appuser:appuser app.jar && chmod 444 app.jar
ENV APP_PROFILE=default
EXPOSE 8080
USER appuser
ENTRYPOINT ["sh", "-c", "java -jar app.jar --spring.profiles.active=$APP_PROFILE"]
