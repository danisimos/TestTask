#FROM openjdk:17-alpine

#COPY target/test-task-0.1.jar /app.jar

#CMD ["java", "-jar", "-Dspring.profiles.active=dev","/app.jar"]
#CMD ["./mvnw", "-version"]

FROM maven:3.8.5-jdk-11
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package