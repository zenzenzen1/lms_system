package com.example.user_service.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreationRequest {
    
    @NotBlank(message = "Account ID is required")
    private String accountId;
    
    @NotBlank(message = "Full name is required")
    private String fullName;
    
    @NotNull(message = "Birth date is required")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    
    @Pattern(regexp = "^[0-9+\\-\\s()]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;
    
    private String address;
}