package com.example.identity_service.dto.response;

import java.util.Set;

import com.example.identity_service.entity.Permission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RoleResponse {
    private String name, description;
    private Set<Permission> permissions;
}
