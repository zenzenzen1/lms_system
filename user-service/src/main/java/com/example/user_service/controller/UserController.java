package com.example.user_service.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_service.dto.request.UserCreationRequest;
import com.example.user_service.dto.request.UserUpdateRequest;
import com.example.user_service.dto.response.ApiResponse;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
// @Tag(name = "User Management", description = "APIs for managing users")
public class UserController {
    
    private final UserService userService;
    
    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "User created successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "User already exists")
    })
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @Valid @RequestBody UserCreationRequest request) {
        log.info("REST request to create user with account ID: {}", request.getAccountId());
        
        UserResponse userResponse = userService.createUser(request);
        ApiResponse<UserResponse> response = ApiResponse.success("User created successfully", userResponse);
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a user by their unique identifier")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @Parameter(description = "User ID") @PathVariable Integer id) {
        log.info("REST request to get user by ID: {}", id);
        
        UserResponse userResponse = userService.getUserById(id);
        ApiResponse<UserResponse> response = ApiResponse.success(userResponse);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/account/{accountId}")
    @Operation(summary = "Get user by account ID", description = "Retrieves a user by their account identifier")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<ApiResponse<UserResponse>> getUserByAccountId(
            @Parameter(description = "Account ID") @PathVariable String accountId) {
        log.info("REST request to get user by account ID: {}", accountId);
        
        UserResponse userResponse = userService.getUserByAccountId(accountId);
        ApiResponse<UserResponse> response = ApiResponse.success(userResponse);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(summary = "Get all users with pagination", description = "Retrieves all users with pagination and sorting")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsers(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "asc") String sortDir) {
        log.info("REST request to get all users - page: {}, size: {}", page, size);
        
        Page<UserResponse> userPage = userService.getAllUsers(page, size, sortBy, sortDir);
        ApiResponse<Page<UserResponse>> response = ApiResponse.success(userPage);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search users", description = "Search users with optional filters")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Search completed successfully")
    })
    public ResponseEntity<ApiResponse<Page<UserResponse>>> searchUsers(
            @Parameter(description = "Full name filter") @RequestParam(required = false) String fullName,
            @Parameter(description = "Phone number filter") @RequestParam(required = false) String phoneNumber,
            @Parameter(description = "Address filter") @RequestParam(required = false) String address,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(defaultValue = "asc") String sortDir) {
        log.info("REST request to search users with filters");
        
        Page<UserResponse> userPage = userService.searchUsers(fullName, phoneNumber, address, page, size, sortBy, sortDir);
        ApiResponse<Page<UserResponse>> response = ApiResponse.success(userPage);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/list")
    @Operation(summary = "Get all users as list", description = "Retrieves all users without pagination")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsersAsList() {
        log.info("REST request to get all users as list");
        
        List<UserResponse> users = userService.getAllUsersAsList();
        ApiResponse<List<UserResponse>> response = ApiResponse.success(users);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user with the provided information")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @Parameter(description = "User ID") @PathVariable Integer id,
            @Valid @RequestBody UserUpdateRequest request) {
        log.info("REST request to update user with ID: {}", id);
        
        UserResponse userResponse = userService.updateUser(id, request);
        ApiResponse<UserResponse> response = ApiResponse.success("User updated successfully", userResponse);
        
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Deletes a user by their unique identifier")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @Parameter(description = "User ID") @PathVariable Integer id) {
        log.info("REST request to delete user with ID: {}", id);
        
        userService.deleteUser(id);
        ApiResponse<Void> response = ApiResponse.success("User deleted successfully", null);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}/exists")
    @Operation(summary = "Check if user exists", description = "Checks if a user exists by their unique identifier")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Check completed")
    })
    public ResponseEntity<ApiResponse<Boolean>> existsById(
            @Parameter(description = "User ID") @PathVariable Integer id) {
        log.info("REST request to check if user exists with ID: {}", id);
        
        boolean exists = userService.existsById(id);
        ApiResponse<Boolean> response = ApiResponse.success(exists);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/account/{accountId}/exists")
    @Operation(summary = "Check if user exists by account ID", description = "Checks if a user exists by their account identifier")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Check completed")
    })
    public ResponseEntity<ApiResponse<Boolean>> existsByAccountId(
            @Parameter(description = "Account ID") @PathVariable String accountId) {
        log.info("REST request to check if user exists with account ID: {}", accountId);
        
        boolean exists = userService.existsByAccountId(accountId);
        ApiResponse<Boolean> response = ApiResponse.success(exists);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/count")
    @Operation(summary = "Get total user count", description = "Retrieves the total number of users")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Count retrieved successfully")
    })
    public ResponseEntity<ApiResponse<Long>> getTotalUserCount() {
        log.info("REST request to get total user count");
        
        long count = userService.getTotalUserCount();
        ApiResponse<Long> response = ApiResponse.success(count);
        
        return ResponseEntity.ok(response);
    }
}