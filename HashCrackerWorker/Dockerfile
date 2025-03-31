FROM openjdk:21

COPY build/libs/*.jar /worker.jar

EXPOSE 8081

CMD ["java", "-jar", "/worker.jar"]