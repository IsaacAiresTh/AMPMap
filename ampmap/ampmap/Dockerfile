# --- Estágio 1: Build da Aplicação com Maven ---
# Usamos uma imagem que já vem com Maven e a versão 21 do Java (a mesma do seu pom.xml)
FROM maven:3.9-eclipse-temurin-21 AS build

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o pom.xml primeiro para aproveitar o cache de dependências do Docker
COPY pom.xml .

# Baixa todas as dependências do projeto
RUN mvn dependency:go-offline

# Copia todo o resto do código-fonte
COPY src ./src

# Compila a aplicação e gera o arquivo .jar, pulando os testes
RUN mvn package -DskipTests


# --- Estágio 2: Imagem Final, otimizada para produção ---
# Usamos uma imagem "slim" que contém apenas o Java Runtime (JRE), resultando numa imagem final menor
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copia o arquivo .jar que foi gerado no estágio de "build" para a imagem final
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080, que é a porta padrão do Spring Boot
EXPOSE 8080

# Comando que será executado quando o contêiner iniciar
ENTRYPOINT ["java", "-jar", "app.jar"]