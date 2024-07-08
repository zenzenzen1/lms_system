package com.example.identity_service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.identity_service.dto.request.RolePermissionRequest;
import com.example.identity_service.dto.request.RoleRequest;
import com.example.identity_service.dto.response.RoleResponse;
import com.example.identity_service.entity.Role;
import com.example.identity_service.mapper.RoleMapper;
import com.example.identity_service.repository.PermissionRepository;
import com.example.identity_service.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream().map(roleMapper::toRoleResponse).toList();
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }

    public void deleteRolePermission(RolePermissionRequest request) {
        var role = roleRepository.findById(request.getRole()).orElseThrow();
        request.getPermissions().forEach(permission -> role.getPermissions()
                .remove(permissionRepository.findById(permission).orElseThrow()));
        roleRepository.save(role);
    }

    public Role updateRolePermission(RolePermissionRequest request) {
        var role = roleRepository.findById(request.getRole()).orElseThrow();
        role.getPermissions().addAll(permissionRepository.findAllById(request.getPermissions()));
        return roleRepository.save(role);
    }
}
