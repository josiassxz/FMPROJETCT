#FROM adoptopenjdk/openjdk8:ubi
#
#ADD target/Clientes-0.0.1-SNAPSHOT.jar app.jar
#
#ENTRYPOINT ["java", "-jar", "/app.jar"]
#
#EXPOSE 8080

# Use uma imagem do OpenJDK 11 como base
FROM openjdk:11.0.11-9-jdk

# Defina um diretório de trabalho
WORKDIR /app

ARG JAR_FILE

# Copie o arquivo JAR para o diretório de trabalho no container
COPY target/${JAR_FILE} /app/fmproject.jar

# Defina o comando de entrada (entrypoint)
ENTRYPOINT ["java", "-jar", "fmproject.jar"]

# Exponha a porta 8080 (se necessário)
EXPOSE 8080

