package com.example.user_service.mapper;

import org.springframework.stereotype.Component;

import com.example.user_service.dto.request.UserCreationRequest;
import com.example.user_service.dto.request.UserUpdateRequest;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.entity.User;

@Component
public class UserMapper {
    
    public User toEntity(UserCreationRequest request) {
        return User.builder()
                .accountId(request.getAccountId())
                .fullName(request.getFullName())
                .birthDate(request.getBirthDate())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .build();
    }
    
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .accountId(user.getAccountId())
                .fullName(user.getFullName())
                .birthDate(user.getBirthDate())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }
    
    public void updateEntity(User user, UserUpdateRequest request) {
        if (request.getFullName() != null) {
            user.setFullName(request.getFullName());
        }
        if (request.getBirthDate() != null) {
            user.setBirthDate(request.getBirthDate());
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        if (request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }
    }
}