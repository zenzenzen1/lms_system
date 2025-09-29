package com.example.user_service.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Integer id;
    private String accountId;
    private String fullName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String address;
}