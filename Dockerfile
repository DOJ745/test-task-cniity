FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy JAR into container
COPY target/spring-boot-demo-0.0.1.jar /app/app.jar

# Copy file with user collection into main app folder
COPY src/main/resources/static/big-user-data.xml /app/big-user-data.xml

# App launch
ENTRYPOINT ["java", "-jar", "/app/app.jar"]