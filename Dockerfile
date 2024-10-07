# Etapa de construcción
FROM eclipse-temurin:17 AS build
WORKDIR /src

# Instalar Maven
RUN apt-get update && apt-get install -y maven

COPY . .
RUN mvn clean package

# Etapa final
FROM eclipse-temurin:17
COPY --from=build /src/target/MeliUrlShorter-0.0.1-SNAPSHOT.jar melishorter.jar

ENTRYPOINT ["java", "-jar", "melishorter.jar"]
