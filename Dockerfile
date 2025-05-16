FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/book_presentation_service-0.0.1-SNAPSHOT.jar book_presentation_service.jar

EXPOSE 8383

ENTRYPOINT ["java", "-jar", "book_presentation_service.jar"]