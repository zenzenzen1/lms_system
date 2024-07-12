package com.example.identity_service.dto.response;

import com.example.identity_service.enums.ResponseCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    @Builder.Default
    private int code = ResponseCode.SUCCESS.getCode();

    private String message;
    private T result;
}
