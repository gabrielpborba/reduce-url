FROM openjdk:8-jdk-alpine
MAINTAINER Gabriel Pastre Borba
VOLUME /tmp
ARG JAR_FILE=target/consumer-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} consumer.jar
EXPOSE 8080
ENTRYPOINT java -jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/consumer.jar"]
