# Dockerfile for Trendy_Back
FROM openjdk:17-jdk-slim
VOLUME /tmp
COPY build/libs/Back-0.0.1-SNAPSHOT.war app.war
ENTRYPOINT ["java", "-jar", "/app.war"]