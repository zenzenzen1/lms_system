package com.example.lms_system.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.dto.request.UserRequest;
import com.example.lms_system.dto.response.UserResponse;
import com.example.lms_system.entity.*;
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

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    public UserResponse updateUser(UserRequest userRequest) {
        var target = userMapper.toUser(userRequest);
        var user = userRepository
                .findById(target.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user = userMapper.toUser(userRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }

    public Page<User> getUsers(int page, int size) {
        List<User> users = userRepository.findAll();

        Pageable pageRequest = createPageRequest(page, size);

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), users.size());

        List<User> pageContent = users.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, users.size());
    }

    public List<User> getUserListFromPage(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        Page<User> users = userRepository.findAll(pageRequest);

        return users.hasContent() ? users.getContent() : Collections.emptyList();
    }
}
