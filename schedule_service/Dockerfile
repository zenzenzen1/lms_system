# Stage 1: build
FROM maven:3.9.8-sapmachine-22 as build
WORKDIR /app/schedule_service
COPY . .
COPY ./settings.xml /root/.m2/settings.xml
RUN mvn -s ./settings.xml install -DskipTests

# Stage 2: run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/schedule_service/target/schedule_service-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
