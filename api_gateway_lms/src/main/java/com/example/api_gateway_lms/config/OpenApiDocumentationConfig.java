package com.example.api_gateway_lms.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiDocumentationConfig {

    @Value("${server.port:8090}")
    private int serverPort;

    @Value("${app.api-prefix}")
    private String apiPrefix;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LMS API Gateway")
                        .version("1.0.0")
                        .description("""
                                API Gateway for Learning Management System (LMS)
                                
                                This gateway provides unified access to all LMS microservices:
                                • Identity Service - Authentication and user management
                                • Schedule Service - Course scheduling and timetable management  
                                • Notification Service - Notifications and messaging
                                
                                All microservice APIs are accessible through this gateway using the prefix: %s
                                """.formatted(apiPrefix))
                        .contact(new Contact()
                                .name("LMS Development Team")
                                .email("lms-dev@example.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Development Server"),
                        new Server()
                                .url(apiPrefix)
                                .description("API Gateway Base Path")
                ));
    }
}
