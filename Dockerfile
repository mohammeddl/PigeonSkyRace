# Stage 1: Build the application
FROM maven:3.8.8-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml ./
COPY src ./src
# Ensure keystore is in resources
COPY keystore.p12 ./src/main/resources/
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/PigeonSkyRace-0.0.1-SNAPSHOT.jar app.jar
COPY --from=builder /app/src/main/resources/keystore.p12 /app/keystore.p12

EXPOSE 8082
EXPOSE 8443

ENTRYPOINT ["java", "-Dserver.ssl.key-store=/app/keystore.p12", "-jar", "app.jar"]