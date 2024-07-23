
FROM maven:3.9.8-sapmachine-22 as build
WORKDIR /app/lms_system_server
COPY . .
# ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db/lms_system
# ENV SPRING_DATASOURCE_USERNAME=postgres
# ENV SPRING_DATASOURCE_PASSWORD=123
# RUN mvn package -f /app/lms_system_server/pom.xml

# FROM openjdk:17-slim
# WORKDIR /app
# COPY --from=build /app/lms_system_server/target/lms_system-0.0.1-SNAPSHOT.jar lms_system.jar

# CMD [ "java", "-jar", "lms_system.jar"]