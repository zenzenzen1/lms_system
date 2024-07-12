package com.example.identity_service.dto.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileCreationRequest {
    private String userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate dob;

    @Default
    private boolean active = true;
}
