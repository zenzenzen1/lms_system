# Stage 1: build
FROM maven:3.9.8-sapmachine-22 AS build
WORKDIR /app/schedule_service

ARG NEXUS_URL="http://localhost:9081/repository/maven-public/"
COPY ../common/settings.xml /root/.m2/settings.xml

COPY schedule_service/ .
# COPY ./settings.xml /root/.m2/settings.xml
COPY ../common /app/common
RUN cd /app/common && mvn clean install -DskipTests && cd /app/schedule_service && mvn install -DskipTests

# Stage 2: run
FROM amazoncorretto:17-alpine
ARG PROFILE=dev
ARG APP_VERSION=0.0.1-SNAPSHOT

WORKDIR /app
COPY --from=build /app/schedule_service/target/schedule_service-${APP_VERSION}.jar /app/
EXPOSE 8086

ENV DB_URL="jdbc:postgresql://schedule-service-db:5432/schedule_service"
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}
# ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-Dspring.datasource.url=${DB_URL}", "/app/schedule_service-${JAR_VERSION}.jar"]
# CMD ["java", "-jar", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-Dspring.datasource.url=${DB_URL}", "schedule_service-${JAR_VERSION}.jar"]
ENTRYPOINT ["sh", "-c", "java -jar -Dspring.profiles.active=$ACTIVE_PROFILE -Dspring.datasource.url=$DB_URL schedule_service-$JAR_VERSION.jar"]
