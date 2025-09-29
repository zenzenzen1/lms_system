package com.example.schedule_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schedule_service.client.IdentityClient;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/public/demo")
public class DemoController {
    private final IdentityClient identityClient;
    
    
    @GetMapping
    public Object getMethodName() {
        return identityClient.getUserByUsername("admin");
    }
    
}
