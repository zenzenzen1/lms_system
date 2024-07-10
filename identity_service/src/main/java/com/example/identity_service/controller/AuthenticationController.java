package com.example.identity_service.controller;

import java.text.ParseException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identity_service.dto.request.AuthenticationRequest;
import com.example.identity_service.dto.request.IntrospectRequest;
import com.example.identity_service.dto.request.LogoutRequest;
import com.example.identity_service.dto.request.RefreshTokenRequest;
import com.example.identity_service.dto.response.ApiResponse;
import com.example.identity_service.dto.response.AuthenticationResponse;
import com.example.identity_service.dto.response.IntrospectResponse;
import com.example.identity_service.enums.ResponseCode;
import com.example.identity_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping({"/signin-with-google"})
    public Object currentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("/token")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.authenticate(request);
        // Authentication information
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("username: {} ", authentication.getName());
        log.info("roles: {}", authentication.getAuthorities());

        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .code(ResponseCode.SUCCESS.getCode())
                .build();
    }

    @PostMapping("/refreshToken")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request)
            throws ParseException, JOSEException {
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.refreshToken(request))
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .code(ResponseCode.SUCCESS.getCode())
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        // SecurityContextHolder.clearContext();
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }
}
