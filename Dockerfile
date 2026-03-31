# Estagio 1: Build usando o JDK 25 e o Wrapper que ja esta no seu projeto
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
COPY . .
# Garante permissao para o script do Maven e compila o projeto
RUN chmod +x mvnw && ./mvnw clean package -DskipTests

# Estagio 2: Execucao usando a JRE 25 (mais leve)
FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]