FROM maven:3.9.8-sapmachine-22 as build
WORKDIR /app/identity_service
COPY . .
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