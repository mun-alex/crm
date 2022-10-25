FROM openjdk:11
MAINTAINER bitlab
COPY /build/libs/crm-0.0.1-SNAPSHOT.jar crm-back.jar
ENTRYPOINT ["java", "-jar", "/crm-back.jar"]