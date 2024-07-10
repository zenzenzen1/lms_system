package com.example.identity_service.configuration;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.identity_service.entity.Role;
import com.example.identity_service.entity.User;
import com.example.identity_service.repository.RoleRepository;
import com.example.identity_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationInitConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findAll().isEmpty()) {
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
                    User user = User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .roles(roles)
                            .build();
                    userRepository.save(user);

                    userRepository.save(User.builder()
                            .username("student1")
                            .password(passwordEncoder.encode("student1"))
                            .roles(Set.of(studentRole, userRole))
                            .build());
                    userRepository.save(User.builder()
                            .username("teacher1")
                            .password(passwordEncoder.encode("teacher1"))
                            .roles(Set.of(teacherRole, userRole))
                            .build());
                    log.warn("Admin user has been created with default password: admin");
                }
            }
        };
    }
    ;
}
