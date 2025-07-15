# Stage 1: build
FROM maven:3.9.8-sapmachine-22 as build
WORKDIR /app/lms_notification_service
COPY . .
COPY ./settings.xml /root/.m2/settings.xml
RUN mvn -s ./settings.xml -f ./pom-prod.xml install -DskipTests

# Stage 2: run
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/lms_notification_service/target/lms_notification_service-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app/app.jar"]



# CMD mvn spring-boot:run -DskipTests
# RUN  cd ../lms_system_server && mvn install
# RUN cd /app/lms_notification_service
# ENV key=value
# RUN mvn install:install-file -DcreateChecksum=true -Dclassifier=sources -Dfile="lms_system-0.0.1-SNAPSHOT.jar" -DgroupId="com.example" -DartifactId="lms_system" -Dversion="0.0.1-SNAPSHOT" -Dpackaging=jar -DgeneratePom=true -Dclassifier=sources
# RUN mvn install


# RUN mvn package -f /app/identity_service/pom.xml
# RUN mvn spring-boot:run -Dspring-boot.run.profiles=prod

# FROM openjdk:17-slim
# WORKDIR /app
# COPY --from=build /app/identity_service/target/identity_service-0.0.1-SNAPSHOT.jar identity_service.jar

# COPY --from=build /app/identity_service/uploads uploads
# FROM openjdk:17
# WORKDIR /app
# COPY ./target/identity_service-0.0.1-SNAPSHOT.jar app.jar

# EXPOSE 8080   
# ENTRYPOINT [ "java", "-jar", "app.jar" ]
# CMD [ "java", "-jar", "identity_service.jar" ]