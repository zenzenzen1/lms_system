# Stage 1: build
FROM maven:3.9.8-sapmachine-22 AS build
WORKDIR /app/identity_service
ARG NEXUS_URL="http://localhost:9081/repository/maven-public/"
COPY identity_service/ .
COPY ../settings.xml /root/.m2/settings.xml
RUN mvn install -DskipTests

# Stage 2: run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
ARG PROFILE=dev
ARG APP_VERSION=0.0.1-SNAPSHOT
COPY --from=build /app/identity_service/target/identity_service-${APP_VERSION}.jar /app/
EXPOSE 8085
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}
# ENTRYPOINT ["java", "-jar", "/app/app.jar"]
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=$ACTIVE_PROFILE /app/identity_service-$JAR_VERSION.jar"]

# RUN mvn package -f /app/identity_service/pom.xml
# RUN mvn spring-boot:run -Dspring-boot.run.profiles=prod

