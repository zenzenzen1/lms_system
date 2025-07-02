package com.example.schedule_service.entity.dto.response.identity_service;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    private String username;

    // @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    // private String firstName, lastName;
    private String fullName;

    // @DobConstraint(min = 18, message = "INVALID_DOB")
    private LocalDate dob;

    private String phoneNumber;
    private String email;
}
