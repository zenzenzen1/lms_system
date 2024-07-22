# FROM maven:3.9.8-sapmachine-22 as build
# # WORKDIR /app
# COPY . /app/identity_service
# RUN mvn package -f /app/identity_service/pom.xml

# FROM openjdk:17-slim
# # WORKDIR /app
# COPY --from=build /app/identity_service/target/identity_service-0.0.1-SNAPSHOT.jar identity_service.jar
# COPY --from=build /app/identity_service/uploads uploads

FROM openjdk:17
WORKDIR /app
COPY ./target/identity_service-0.0.1-SNAPSHOT.jar app.jar
# WORKDIR /app/identity_service

# EXPOSE 8080
# ENTRYPOINT [ "java", "-jar", "app.jar" ]
CMD [ "java", "-jar", "app.jar" ]