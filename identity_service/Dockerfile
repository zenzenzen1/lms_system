# Stage 1: build
FROM 3.9.11-amazoncorretto-17-alpine AS build
WORKDIR /app/identity_service

ARG NEXUS_URL="http://localhost:9081/repository/maven-public/"
COPY ../settings.xml /root/.m2/settings.xml

COPY identity_service/ .
RUN mvn install -DskipTests

# Stage 2: run
FROM amazoncorretto:17.0.16-alpine3.22
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

