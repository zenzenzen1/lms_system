package com.example.lms_system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.lms_system.dto.request.UserRequest;
import com.example.lms_system.dto.response.UserResponse;
import com.example.lms_system.mapper.UserMapper;
import com.example.lms_system.repository.UserRepository;
import com.example.lms_system.repository.http_client.IdentityClient;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IdentityClient identityClient;

    public UserResponse updateUser(UserRequest userRequest) {
        var user = userMapper.toUser(userRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse insertUser(UserRequest userRequest) {
        var user = userMapper.toUser(userRequest);
        if (userRepository.findByUserId(user.getUserId()).isPresent()) {
            return null;
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    public Object getUserByRole(String role) {
        // return userRepository.findAll().stream()
        //         .map(user -> userMapper.toUserResponse(user))
        //         .collect(Collectors.toSet());
        var res = identityClient.getUserByRole(role);
        return res;
    }
}
