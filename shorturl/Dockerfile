FROM openjdk:8-jdk-alpine
MAINTAINER Gabriel Pastre Borba
VOLUME /tmp
ARG JAR_FILE=target/shorturl-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} shorturl.jar
EXPOSE 8080
ENTRYPOINT java -jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/shorturl.jar"]
