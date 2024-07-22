package com.example.identity_service.dto.request;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    private String password;
    private String firstName, lastName;
    private LocalDate dob;
    private boolean active;
    Set<String> roles;
}
