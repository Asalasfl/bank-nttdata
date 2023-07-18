# Establece la imagen base de Java
FROM openjdk:11-jdk

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia los archivos necesarios al contenedor
COPY target/config-server-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties application.properties

# Expone el puerto del Config Server (si es necesario)
EXPOSE 8888

# Comando para ejecutar el Config Server cuando se inicie el contenedor
CMD ["java", "-jar", "app.jar"]
