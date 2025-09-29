package com.example.dto.identity_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class IntrospectResponse {
    private boolean isValid;
    private String userId;
    private String roles;
}
