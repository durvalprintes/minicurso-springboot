FROM maven:3.8.6-eclipse-temurin-11-alpine
LABEL AUTH="DURVAL PRINTES"

VOLUME /root/api
VOLUME /root/.m2

WORKDIR /root/api
EXPOSE 9000
ENTRYPOINT mvn spring-boot:run -Dspring-boot.run.profiles=dev