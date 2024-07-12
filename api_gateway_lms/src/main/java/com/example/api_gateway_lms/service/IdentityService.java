package com.example.api_gateway_lms.service;

import org.springframework.stereotype.Service;

import com.example.api_gateway_lms.dto.request.IntrospectRequest;
import com.example.api_gateway_lms.dto.response.ApiResponse;
import com.example.api_gateway_lms.dto.response.IntrospectResponse;
import com.example.api_gateway_lms.repository.IdentityClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IdentityService {
    private final IdentityClient identityClient;
    
    public Mono<ApiResponse<IntrospectResponse>> introspect(String token){
        return identityClient.introspect(IntrospectRequest.builder().token(token).build());
    }
}
