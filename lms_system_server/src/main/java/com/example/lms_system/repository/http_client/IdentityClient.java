package com.example.lms_system.repository.http_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.lms_system.configuration.AuthenticationRequestInterceptor;
import com.example.lms_system.dto.response.identity_service.UserCreationRequest;
import com.example.lms_system.dto.response.identity_service.UserResponse;

@FeignClient(
        name = "identity",
        url = "${app.services.identity}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface IdentityClient {
    @GetMapping(value = "/users/getUserByRole/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    Object getUserByRole(@PathVariable("role") String role);

    @GetMapping(value = "/users/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    UserResponse getUserByUsername(@PathVariable String username);

    @PostMapping(value = "/users/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    UserResponse createUser(@RequestBody UserCreationRequest request);
}
