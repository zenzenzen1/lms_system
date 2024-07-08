package com.example.lms_system.service;

import org.springframework.stereotype.Service;

import com.example.lms_system.dto.request.UserRequest;
import com.example.lms_system.dto.response.UserResponse;
import com.example.lms_system.mapper.UserMapper;
import com.example.lms_system.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse insertUser(UserRequest userRequest) {
        var user = userMapper.toUser(userRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
