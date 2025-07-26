package com.example.api_gateway_lms.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${app.api-prefix}")
    private String apiPrefix;

    @Bean
    @Lazy(false)
    public List<GroupedOpenApi> apis(RouteDefinitionLocator locator, SwaggerUiConfigParameters swaggerUiConfigParameters) {
        List<GroupedOpenApi> groups = new ArrayList<>();
        
        // Get all route definitions
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
        
        if (definitions != null) {
            definitions.forEach(routeDefinition -> {
                String name = routeDefinition.getId();
                
                // Create a grouped API for each service
                GroupedOpenApi.Builder builder = GroupedOpenApi.builder()
                        .group(name)
                        .pathsToMatch(apiPrefix + "/" + name + "/**");
                
                groups.add(builder.build());
                
                // Add the service to swagger UI configuration
                swaggerUiConfigParameters.addGroup(name);
            });
        }
        
        // Add a default group for gateway-specific endpoints
        groups.add(GroupedOpenApi.builder()
                .group("api-gateway")
                .pathsToMatch("/actuator/**")
                .build());
        
        swaggerUiConfigParameters.addGroup("api-gateway");
        
        return groups;
    }
}
