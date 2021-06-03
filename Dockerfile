FROM openjdk:8-alpine
MAINTAINER "Omari Sopromadze"
WORKDIR /app

COPY ./target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

EXPOSE 8080