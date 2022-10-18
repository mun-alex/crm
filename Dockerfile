FROM openjdk:11
MAINTAINER bitlab
COPY crm-0.0.1-SNAPSHOT.jar crm-bitlab.jar
ENTRYPOINT ["java", "-jar", "/crm-bitlab.jar"]