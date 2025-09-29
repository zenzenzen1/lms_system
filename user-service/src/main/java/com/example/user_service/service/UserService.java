package com.example.user_service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.user_service.dto.request.UserCreationRequest;
import com.example.user_service.dto.request.UserUpdateRequest;
import com.example.user_service.dto.response.UserResponse;
import com.example.user_service.entity.User;
import com.example.user_service.exception.UserAlreadyExistsException;
import com.example.user_service.exception.UserNotFoundException;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    
    public UserResponse createUser(UserCreationRequest request) {
        log.info("Creating user with account ID: {}", request.getAccountId());
        
        if (userRepository.existsByAccountId(request.getAccountId())) {
            throw UserAlreadyExistsException.withAccountId(request.getAccountId());
        }
        
        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);
        
        log.info("User created successfully with ID: {}", savedUser.getId());
        return userMapper.toResponse(savedUser);
    }
    
    @Transactional(readOnly = true)
    public UserResponse getUserById(Integer id) {
        log.info("Fetching user with ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        return userMapper.toResponse(user);
    }
    
    @Transactional(readOnly = true)
    public UserResponse getUserByAccountId(String accountId) {
        log.info("Fetching user with account ID: {}", accountId);
        
        User user = userRepository.findByAccountId(accountId)
                .orElseThrow(() -> new UserNotFoundException("accountId", accountId));
        
        return userMapper.toResponse(user);
    }
    
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(int page, int size, String sortBy, String sortDir) {
        log.info("Fetching users - page: {}, size: {}, sortBy: {}, sortDir: {}", page, size, sortBy, sortDir);
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<User> userPage = userRepository.findAll(pageable);
        
        return userPage.map(userMapper::toResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<UserResponse> searchUsers(String fullName, String phoneNumber, String address, 
                                        int page, int size, String sortBy, String sortDir) {
        log.info("Searching users with filters - fullName: {}, phoneNumber: {}, address: {}", 
                fullName, phoneNumber, address);
        
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<User> userPage = userRepository.findUsersWithFilters(fullName, phoneNumber, address, pageable);
        
        return userPage.map(userMapper::toResponse);
    }
    
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsersAsList() {
        log.info("Fetching all users as list");
        
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }
    
    public UserResponse updateUser(Integer id, UserUpdateRequest request) {
        log.info("Updating user with ID: {}", id);
        
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        userMapper.updateEntity(existingUser, request);
        User updatedUser = userRepository.save(existingUser);
        
        log.info("User updated successfully with ID: {}", updatedUser.getId());
        return userMapper.toResponse(updatedUser);
    }
    
    public void deleteUser(Integer id) {
        log.info("Deleting user with ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }
    
    @Transactional(readOnly = true)
    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }
    
    @Transactional(readOnly = true)
    public boolean existsByAccountId(String accountId) {
        return userRepository.existsByAccountId(accountId);
    }
    
    @Transactional(readOnly = true)
    public long getTotalUserCount() {
        return userRepository.count();
    }
}