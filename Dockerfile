
FROM eclipse-temurin:17

# En este paso lo que hacemos es copiar un conjunto de archivos de mi maquina host a mi contenedor
COPY target/MeliUrlShorter-0.0.1-SNAPSHOT.jar melishorter.jar

#Ejecutamos el jar
ENTRYPOINT ["java", "-jar", "melishorter.jar"]