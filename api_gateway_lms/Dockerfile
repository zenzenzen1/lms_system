FROM maven:3.9.8-sapmachine-22 AS build
WORKDIR /app/api_gateway
COPY . .
RUN mvn install -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ARG APP_VERSION=0.0.1-SNAPSHOT
COPY --from=build /app/api_gateway/target/api_gateway_lms-${APP_VERSION}.jar /app/
EXPOSE 8090
ENV ACTIVE_PROFILE=dev
ENV JAR_VERSION=${APP_VERSION}
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=$ACTIVE_PROFILE /app/api_gateway_lms-${JAR_VERSION}.jar"]
# COPY ./src/main/resources/application-prod.yaml src/main/resources/application.yaml
# ENV SPRING_DATASOURCE_URL=jdbc:postgresql://db/lms_system
# ENV SPRING_DATASOURCE_USERNAME=postgres
# ENV SPRING_DATASOURCE_PASSWORD=123
# RUN mvn package -f /app/lms_system_server/pom.xml

# FROM openjdk:17-slim
# WORKDIR /app
# COPY --from=build /app/lms_system_server/target/lms_system-0.0.1-SNAPSHOT.jar lms_system.jar

# CMD [ "java", "-jar", "lms_system.jar"]