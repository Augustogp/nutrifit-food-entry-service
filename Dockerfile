FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/food-entry-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 4004
EXPOSE 9004

ENTRYPOINT ["java", "-jar", "app.jar"]