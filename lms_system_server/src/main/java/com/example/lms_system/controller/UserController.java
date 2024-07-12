package com.example.lms_system.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms_system.dto.request.UserRequest;
import com.example.lms_system.dto.response.ApiResponse;
import com.example.lms_system.dto.response.UserResponse;
import com.example.lms_system.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/users", "/users/"})
public class UserController {
    private final UserService userService;

    @PutMapping
    public ApiResponse<UserResponse> updateUser(@RequestBody UserRequest userRequest) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(userRequest))
                .build();
    }

    @PostMapping
    public UserResponse insertUser(@RequestBody UserRequest userRequest) {
        return userService.insertUser(userRequest);
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUser() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUser())
                .build();
    }

    @PostMapping("/deleteAllUsers")
    public void postMethodName() {
        userService.deleteAllUser();
    }
}
