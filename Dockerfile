FROM openjdk:17-alpine

COPY target/test-task-0.1.jar /app.jar

CMD ["java", "-jar", "-Dspring.profiles.active=dev","/app.jar"]