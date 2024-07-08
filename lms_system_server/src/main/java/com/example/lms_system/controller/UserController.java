package com.example.lms_system.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms_system.dto.request.UserRequest;
import com.example.lms_system.dto.response.UserResponse;
import com.example.lms_system.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/users", "/users/"})
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserResponse postMethodName(@RequestBody UserRequest userRequest) {
        return userService.insertUser(userRequest);
    }
}
