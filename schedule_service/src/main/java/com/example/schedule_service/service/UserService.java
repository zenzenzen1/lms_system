package com.example.schedule_service.service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.schedule_service.entity.User;
import com.example.schedule_service.entity.dto.request.UserRequest;
import com.example.schedule_service.entity.dto.response.UserResponse;
import com.example.schedule_service.mapper.UserMapper;
import com.example.schedule_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    // private final IdentityClient identityClient;

    public Set<UserResponse> getStudentsByScheduleId(long scheduleId) {
        return userRepository.getStudentsByScheduleId(scheduleId).stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toSet());
    }

    public UserResponse updateUser(UserRequest userRequest) {
        var target = userMapper.toUser(userRequest);
        var user = userRepository
                .findById(target.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user = userMapper.toUser(userRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public UserResponse insertUser(UserRequest userRequest) {
        var user = userMapper.toUser(userRequest);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getAllUser() {
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    public UserResponse getUserByUserId(String userId) {
        return userRepository.findByUserId(userId)
                .map(userMapper::toUserResponse)
                .orElse(null);
    }

    // public Object getUserByRole(String role) {
    //     // return userRepository.findAll().stream()
    //     // .map(user -> userMapper.toUserResponse(user))
    //     // .collect(Collectors.toSet());
    //     var res = identityClient.getUserByRole(role);
    //     return res;
    // }

    public void deleteById(String id) {
        userRepository.deleteById(id);
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
