FROM openjdk:21

COPY build/libs/*.jar /manager.jar

EXPOSE 8080

CMD ["java", "-jar", "/manager.jar"]