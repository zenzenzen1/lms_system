package com.example.identity_service.controller;

import java.util.List;
import java.util.Set;

import jakarta.validation.Valid;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.ApiResponse;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.entity.User;
import com.example.identity_service.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping({"/users", "/users/"})
@AllArgsConstructor
@Slf4j
public class UserController {
    private UserService userService;
    // private UserProfileClient userProfileClient;

    // @GetMapping("/userProfile/{userId}")
    // public Object getUserProfile(@PathVariable String userId) {
    //     return userProfileClient.getUserProfile(userId);
    // }

    @PostMapping({"/registration"})
    public ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.createUser(request));
        return response;
    }

    @GetMapping({"/userInfo"})
    public ApiResponse<UserResponse> getUserInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserInfo())
                .build();
    }

    @GetMapping
    public ApiResponse<List<User>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("username: {} ", authentication.getName());
        log.info("roles: {}", authentication.getAuthorities());
        return ApiResponse.<List<User>>builder().result(userService.getUsers()).build();
    }

    @GetMapping("/username/{username}")
    public UserResponse getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "User has been deleted";
    }

    @GetMapping("/getUserByRole/{role}")
    public Set<UserResponse> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }
}
