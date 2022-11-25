FROM maven:3.8.6-eclipse-temurin-11-alpine
LABEL AUTH="DURVAL PRINTES"

VOLUME /root/api
VOLUME /root/.m2

ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS

WORKDIR /root/api
EXPOSE 9000 5005
ENTRYPOINT mvn spring-boot:run -Dspring-boot.run.profiles=dev -Dspring-boot.run.jvmArguments="$JAVA_OPTS"