FROM eclipse-temurin:17-jdk-alpine

# Cria usuário não-root (requisito)
RUN adduser -D -H appuser

# Define diretório de trabalho (requisito)
WORKDIR /app

# Copia o jar para o container
COPY target/safehub-0.0.1-SNAPSHOT.jar app.jar

# Ajusta permissões para o usuário não-root
RUN chown appuser:appuser app.jar

# Exemplo de variável de ambiente (requisito)
ENV APP_PROFILE=default

# Expõe a porta da aplicação (requisito)
EXPOSE 8080

# Troca para usuário não-root (requisito)
USER appuser

# Comando de inicialização (requisito)
ENTRYPOINT ["sh", "-c", "java -jar app.jar --spring.profiles.active=$APP_PROFILE"]