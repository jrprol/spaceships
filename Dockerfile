# JDK 17 image
FROM openjdk:17-jdk-slim

# Working directory
WORKDIR /app

# JAR application file to docker
COPY target/spaceship-api-1.0.0.jar app.jar

# Expose port aplication
EXPOSE 8080

# Entry point for the application
ENTRYPOINT ["java", "-jar", "app.jar"]
