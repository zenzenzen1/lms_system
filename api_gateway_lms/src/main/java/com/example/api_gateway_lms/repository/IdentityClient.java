package com.example.api_gateway_lms.repository;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

import com.example.api_gateway_lms.dto.request.IntrospectRequest;
import com.example.api_gateway_lms.dto.response.ApiResponse;
import com.example.api_gateway_lms.dto.response.IntrospectResponse;

import reactor.core.publisher.Mono;

public interface IdentityClient {
    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
