FROM openjdk:17-alpine

WORKDIR /app

EXPOSE 8080

COPY ./build/libs/*.jar resume-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/app/resume-0.0.1-SNAPSHOT.jar"]
