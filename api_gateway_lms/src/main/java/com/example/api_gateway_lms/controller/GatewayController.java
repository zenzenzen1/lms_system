package com.example.api_gateway_lms.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/gateway")
@Tag(name = "API Gateway", description = "API Gateway management endpoints")
public class GatewayController {

    private final RouteDefinitionLocator routeDefinitionLocator;
    
    @Value("${app.api-prefix}")
    private String apiPrefix;

    public GatewayController(RouteDefinitionLocator routeDefinitionLocator) {
        this.routeDefinitionLocator = routeDefinitionLocator;
    }

    @GetMapping("/routes")
    @Operation(summary = "Get all available routes", description = "Returns all configured routes in the API Gateway")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved routes")
    })
    public Flux<Map<String, Object>> getRoutes() {
        return routeDefinitionLocator.getRouteDefinitions()
                .map(route -> Map.of(
                        "id", route.getId(),
                        "uri", route.getUri().toString(),
                        "predicates", route.getPredicates().toString(),
                        "filters", route.getFilters().toString(),
                        "apiPath", apiPrefix + "/" + route.getId() + "/**"
                ));
    }

    @GetMapping("/health")
    @Operation(summary = "Gateway health check", description = "Check if the API Gateway is running properly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gateway is healthy")
    })
    public Mono<Map<String, Object>> health() {
        return Mono.just(Map.of(
                "status", "UP",
                "gateway", "LMS API Gateway",
                "version", "1.0.0",
                "timestamp", System.currentTimeMillis()
        ));
    }

    @GetMapping("/services")
    @Operation(summary = "Get available services", description = "Returns information about all services accessible through this gateway")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved services information")
    })
    public Flux<Map<String, Object>> getServices() {
        return routeDefinitionLocator.getRouteDefinitions()
                .map(route -> Map.of(
                        "serviceName", route.getId(),
                        "serviceUrl", route.getUri().toString(),
                        "gatewayPath", apiPrefix + "/" + route.getId(),
                        "description", getServiceDescription(route.getId()),
                        "swaggerUrl", "/v3/api-docs/" + route.getId()
                ));
    }

    private String getServiceDescription(String serviceId) {
        return switch (serviceId) {
            case "identity_service" -> "Authentication and user management service";
            case "lms_schedule_service" -> "Schedule and timetable management service";
            case "notification_service" -> "Notification and messaging service";
            default -> "LMS microservice: " + serviceId;
        };
    }
}
