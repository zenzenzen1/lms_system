package com.example.identity_service.configuration;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.identity_service.dto.request.UserProfileCreationRequest;
import com.example.identity_service.entity.Role;
import com.example.identity_service.entity.User;
import com.example.identity_service.mapper.UserProfileMapper;
import com.example.identity_service.repository.RoleRepository;
import com.example.identity_service.repository.UserRepository;
import com.example.identity_service.repository.http_client.LmsClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(
            UserRepository userRepository,
            RoleRepository roleRepository,
            LmsClient lmsClient,
            UserProfileMapper userProfileMapper) {
        return args -> {
            if (roleRepository.findAll().isEmpty()) {
                lmsClient.deleteAllUser();
                var adminRole = roleRepository.save(Role.builder()
                        .name(com.example.identity_service.enums.Role.ADMIN.name())
                        .description("Admin role")
                        .build());
                var userRole = roleRepository.save(Role.builder()
                        .name(com.example.identity_service.enums.Role.USER.name())
                        .description("User role")
                        .build());
                var studentRole = roleRepository.save(Role.builder()
                        .name(com.example.identity_service.enums.Role.STUDENT.name())
                        .description("User role")
                        .build());
                var teacherRole = roleRepository.save(Role.builder()
                        .name(com.example.identity_service.enums.Role.TEACHER.name())
                        .description("User role")
                        .build());
                if (userRepository.findByUsername("admin").isEmpty()) {
                    var roles = new HashSet<Role>();
                    roles.add(adminRole);
                    roles.add(userRole);
                    User adminUser = User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .roles(roles)
                            .build();
                    adminUser = userRepository.save(adminUser);

                    var studentUser = userRepository.save(User.builder()
                            .username("student1")
                            .password(passwordEncoder.encode("student1"))
                            .roles(Set.of(studentRole, userRole))
                            .build());
                    lmsClient.createUserProfile(UserProfileCreationRequest.builder()
                            .dob(LocalDate.of(2003, 7, 16))
                            .fullName("Lam Thon")
                            .userId(studentUser.getId())
                            .phoneNumber("19001002")
                            .email("lamthon@gmail.com")
                            .build());
                    var teacherUser = userRepository.save(User.builder()
                            .username("teacher1")
                            .password(passwordEncoder.encode("teacher1"))
                            .roles(Set.of(teacherRole, userRole))
                            .build());
                    lmsClient.createUserProfile(UserProfileCreationRequest.builder()
                            .dob(LocalDate.of(1999, 7, 16))
                            .fullName("Kien NT")
                            .userId(teacherUser.getId())
                            .email("teacher@gmail.com")
                            .phoneNumber("19001002")
                            .build());
                    log.warn("Admin user has been created with default password: admin");
                }
            }
        };
    }
    ;
}
