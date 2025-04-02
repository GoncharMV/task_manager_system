FROM maven:3.9.2-eclipse-temurin-11-alpine AS build
COPY ./src /usr/src/tms/src
COPY ./pom.xml /usr/src/tms
RUN mvn -f /usr/src/tms/pom.xml clean install

### Run
FROM azul/zulu-openjdk-alpine:21
COPY ./target/tms-0.0.1-SNAPSHOT.jar /usr/src/tms/app.jar
EXPOSE 8089
ENTRYPOINT ["java","-jar","/usr/src/tms/app.jar"]
