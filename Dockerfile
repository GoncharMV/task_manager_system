FROM amazoncorretto:21.0.4-alpine3.18
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8060
ENTRYPOINT ["java", "-jar", "app.jar"]
