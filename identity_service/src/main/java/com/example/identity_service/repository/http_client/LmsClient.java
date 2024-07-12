package com.example.identity_service.repository.http_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.identity_service.configuration.AuthenticationRequestInterceptor;
import com.example.identity_service.dto.request.UserProfileCreationRequest;
import com.example.identity_service.dto.response.UserProfileResponse;

@FeignClient(
        name = "lms",
        url = "${app.services.lms}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface LmsClient {
    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileResponse createUserProfile(
            // @RequestHeader("Authorization") String token,
            @RequestBody UserProfileCreationRequest request);

    @PostMapping(value = "/users/deleteAllUsers")
    void deleteAllUser();
}
