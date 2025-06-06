FROM openjdk:17-jdk-slim
VOLUME /tmp
WORKDIR /app
COPY target/eMSPAccountCardService-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
