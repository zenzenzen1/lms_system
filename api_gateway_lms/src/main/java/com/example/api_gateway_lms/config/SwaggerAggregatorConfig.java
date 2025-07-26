package com.example.api_gateway_lms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SwaggerAggregatorConfig {

    @Value("${app.api-prefix}")
    private String apiPrefix;

    @Bean
    public RouterFunction<ServerResponse> swaggerRouterFunction(RouteDefinitionLocator locator) {
        return route(GET("/v3/api-docs/{service}"), request -> {
            String service = request.pathVariable("service");
            
            // Get the route definition for the service
            return locator.getRouteDefinitions()
                    .filter(route -> route.getId().equals(service))
                    .next()
                    .flatMap(routeDefinition -> {
                        // Generate mock API documentation for each service
                        String apiDoc = generateServiceApiDoc(service);
                        return ServerResponse.ok()
                                .header("Content-Type", "application/json")
                                .bodyValue(apiDoc);
                    })
                    .switchIfEmpty(ServerResponse.notFound().build());
        });
    }

    private String generateServiceApiDoc(String service) {
        return switch (service) {
            case "identity_service" -> generateIdentityServiceDoc();
            case "lms_schedule_service" -> generateScheduleServiceDoc();
            case "notification_service" -> generateNotificationServiceDoc();
            default -> generateDefaultServiceDoc(service);
        };
    }

    private String generateIdentityServiceDoc() {
        return """
        {
          "openapi": "3.0.1",
          "info": {
            "title": "Identity Service API",
            "version": "1.0.0",
            "description": "Authentication and user management service for LMS"
          },
          "servers": [
            {
              "url": "%s/identity",
              "description": "Identity Service via API Gateway"
            }
          ],
          "paths": {
            "/login": {
              "post": {
                "summary": "User login",
                "description": "Authenticate user and return JWT token",
                "requestBody": {
                  "content": {
                    "application/json": {
                      "schema": {
                        "type": "object",
                        "properties": {
                          "username": {"type": "string"},
                          "password": {"type": "string"}
                        }
                      }
                    }
                  }
                },
                "responses": {
                  "200": {
                    "description": "Login successful",
                    "content": {
                      "application/json": {
                        "schema": {
                          "type": "object",
                          "properties": {
                            "token": {"type": "string"},
                            "user": {"$ref": "#/components/schemas/User"}
                          }
                        }
                      }
                    }
                  }
                }
              }
            },
            "/register": {
              "post": {
                "summary": "User registration",
                "description": "Register a new user account",
                "responses": {
                  "201": {"description": "User created successfully"}
                }
              }
            },
            "/profile": {
              "get": {
                "summary": "Get user profile",
                "description": "Retrieve current user profile information",
                "responses": {
                  "200": {"description": "Profile retrieved successfully"}
                }
              }
            }
          },
          "components": {
            "schemas": {
              "User": {
                "type": "object",
                "properties": {
                  "id": {"type": "integer"},
                  "username": {"type": "string"},
                  "email": {"type": "string"},
                  "role": {"type": "string"}
                }
              }
            }
          }
        }
        """.formatted(apiPrefix);
    }

    private String generateScheduleServiceDoc() {
        return """
        {
          "openapi": "3.0.1",
          "info": {
            "title": "Schedule Service API",
            "version": "1.0.0",
            "description": "Course scheduling and timetable management service"
          },
          "servers": [
            {
              "url": "%s/lms_schedule_service",
              "description": "Schedule Service via API Gateway"
            }
          ],
          "paths": {
            "/schedules": {
              "get": {
                "summary": "Get schedules",
                "description": "Retrieve schedules for a user or course",
                "parameters": [
                  {
                    "name": "userId",
                    "in": "query",
                    "schema": {"type": "integer"}
                  },
                  {
                    "name": "courseId",
                    "in": "query",
                    "schema": {"type": "integer"}
                  }
                ],
                "responses": {
                  "200": {
                    "description": "Schedules retrieved successfully",
                    "content": {
                      "application/json": {
                        "schema": {
                          "type": "array",
                          "items": {"$ref": "#/components/schemas/Schedule"}
                        }
                      }
                    }
                  }
                }
              },
              "post": {
                "summary": "Create schedule",
                "description": "Create a new schedule entry",
                "responses": {
                  "201": {"description": "Schedule created successfully"}
                }
              }
            },
            "/attendance": {
              "post": {
                "summary": "Mark attendance",
                "description": "Record student attendance for a schedule",
                "responses": {
                  "200": {"description": "Attendance recorded successfully"}
                }
              }
            }
          },
          "components": {
            "schemas": {
              "Schedule": {
                "type": "object",
                "properties": {
                  "id": {"type": "integer"},
                  "courseId": {"type": "integer"},
                  "courseName": {"type": "string"},
                  "startTime": {"type": "string", "format": "date-time"},
                  "endTime": {"type": "string", "format": "date-time"},
                  "room": {"type": "string"}
                }
              }
            }
          }
        }
        """.formatted(apiPrefix);
    }

    private String generateNotificationServiceDoc() {
        return """
        {
          "openapi": "3.0.1",
          "info": {
            "title": "Notification Service API",
            "version": "1.0.0",
            "description": "Notification and messaging service for LMS"
          },
          "servers": [
            {
              "url": "%s/notification",
              "description": "Notification Service via API Gateway"
            }
          ],
          "paths": {
            "/send": {
              "post": {
                "summary": "Send notification",
                "description": "Send a notification to users",
                "requestBody": {
                  "content": {
                    "application/json": {
                      "schema": {
                        "type": "object",
                        "properties": {
                          "recipients": {
                            "type": "array",
                            "items": {"type": "string"}
                          },
                          "message": {"type": "string"},
                          "type": {"type": "string", "enum": ["EMAIL", "SMS", "PUSH"]}
                        }
                      }
                    }
                  }
                },
                "responses": {
                  "200": {"description": "Notification sent successfully"}
                }
              }
            },
            "/notifications": {
              "get": {
                "summary": "Get notifications",
                "description": "Retrieve notifications for a user",
                "parameters": [
                  {
                    "name": "userId",
                    "in": "query",
                    "required": true,
                    "schema": {"type": "integer"}
                  }
                ],
                "responses": {
                  "200": {
                    "description": "Notifications retrieved successfully",
                    "content": {
                      "application/json": {
                        "schema": {
                          "type": "array",
                          "items": {"$ref": "#/components/schemas/Notification"}
                        }
                      }
                    }
                  }
                }
              }
            }
          },
          "components": {
            "schemas": {
              "Notification": {
                "type": "object",
                "properties": {
                  "id": {"type": "integer"},
                  "message": {"type": "string"},
                  "type": {"type": "string"},
                  "timestamp": {"type": "string", "format": "date-time"},
                  "read": {"type": "boolean"}
                }
              }
            }
          }
        }
        """.formatted(apiPrefix);
    }

    private String generateDefaultServiceDoc(String service) {
        return """
        {
          "openapi": "3.0.1",
          "info": {
            "title": "%s API",
            "version": "1.0.0",
            "description": "API documentation for %s"
          },
          "servers": [
            {
              "url": "%s/%s",
              "description": "%s server via API Gateway"
            }
          ],
          "paths": {},
          "components": {}
        }
        """.formatted(service, service, apiPrefix, service, service);
    }
}
