package com.example.lms_system.repository.http_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.lms_system.configuration.AuthenticationRequestInterceptor;

@FeignClient(
        name = "identity",
        url = "${app.services.identity}",
        configuration = {AuthenticationRequestInterceptor.class})
public interface IdentityClient {
    @GetMapping(value = "/users/getUserByRole/{role}", produces = MediaType.APPLICATION_JSON_VALUE)
    Object getUserByRole(@PathVariable("role") String role);
}
