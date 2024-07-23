package com.example.identity_service.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.entity.User;
import com.example.identity_service.enums.Role;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.mapper.UserMapper;
import com.example.identity_service.mapper.UserProfileMapper;
import com.example.identity_service.repository.RoleRepository;
import com.example.identity_service.repository.UserRepository;
import com.example.identity_service.repository.http_client.LmsClient;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    LmsClient lmsClient;
    UserProfileMapper userProfileMapper;

    public Set<UserResponse> getUsersByRole(String role) {
        return userRepository.findAll().stream()
                .filter(user ->
                        user.getRoles().stream().anyMatch(t -> t.getName().equalsIgnoreCase(role)))
                .map(user -> userMapper.toUserResponse(user))
                .collect(Collectors.toSet());
    }

    public User createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            // throw new ArrayIndexOutOfBoundsException("ErrorCode.USER_EXISTED");
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = new User();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userMapper.toUser(request);

        HashSet<com.example.identity_service.entity.Role> roles = new HashSet<>();
        roleRepository.findById(Role.USER.name()).ifPresent(roles::add);
        roleRepository.findById(Role.STUDENT.name()).ifPresent(roles::add);
        user.setRoles(roles);
        user = userRepository.save(user);

        var profileRequest = userProfileMapper.toUserProfileCreationRequest(request);
        profileRequest.setUserId(user.getId());

        // ServletRequestAttributes servletRequestAttributes =
        // (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // var authHeader =
        // servletRequestAttributes.getRequest().getHeader("Authorization");
        // log.info("Auth header: {}", authHeader);

        System.out.println(profileRequest);
        var userProfile = lmsClient.createUserProfile(profileRequest);
        log.info("User profile: {}", userProfile);

        return user;
    }

    // @PostAuthorize(returnObject)
    // @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN', 'SCOPE_GET_ALL_USERS')")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    // @PostAuthorize("returnObject.username == authentication.name")
    // @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        var user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    public UserResponse getUserInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        context.getAuthentication().getAuthorities().forEach(grantedAuthority -> {
            log.warn("Authority: {}", grantedAuthority.getAuthority());
        });
        String name = context.getAuthentication().getName();
        log.warn("{} logging", name);
        User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        var userResponse = userMapper.toUserResponse(user);
        userResponse.setUserProfile(lmsClient.getUserProfileByUserId(user.getId()));
        return userResponse;
    }

    public UserResponse getUserByUsername(String username) {
        return userMapper.toUserResponse(
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        request.setPassword(encodePassword(request.getPassword()));
        userMapper.updateUser(user, request);
        user.setRoles(new HashSet<>(roleRepository.findAllById(request.getRoles())));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
