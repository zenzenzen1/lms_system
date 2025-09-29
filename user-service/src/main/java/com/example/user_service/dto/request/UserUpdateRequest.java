package com.example.user_service.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    
    private String fullName;
    
    private LocalDate birthDate;
    
    @Pattern(regexp = "^[0-9+\\-\\s()]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;
    
    private String address;
}