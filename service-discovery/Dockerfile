# Establece la imagen base de Java
FROM openjdk:11-jdk

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo JAR del servicio Eureka al contenedor
COPY target/service-discovery-1.0.jar app.jar

# Expone el puerto del servicio
EXPOSE 8761

# Comando para ejecutar el servicio Eureka cuando se inicie el contenedor
CMD ["java", "-jar", "app.jar"]

