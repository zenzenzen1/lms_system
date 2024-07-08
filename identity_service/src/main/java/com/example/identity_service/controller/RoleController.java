package com.example.identity_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.identity_service.dto.request.RolePermissionRequest;
import com.example.identity_service.dto.request.RoleRequest;
import com.example.identity_service.dto.response.ApiResponse;
import com.example.identity_service.dto.response.RoleResponse;
import com.example.identity_service.entity.Role;
import com.example.identity_service.enums.ResponseCode;
import com.example.identity_service.service.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping({"/roles"})
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> create(@RequestBody final RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(roleRequest))
                .code(ResponseCode.SUCCESS.getCode())
                .build();
    }

    @GetMapping()
    public ApiResponse<List<RoleResponse>> getPermissions() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .code(ResponseCode.SUCCESS.getCode())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String role) {
        roleService.delete(role);
        return ApiResponse.<Void>builder().code(ResponseCode.SUCCESS.getCode()).build();
    }

    @DeleteMapping("/deleteRolePermission")
    ApiResponse<Void> deleteRolePermission(@RequestBody final RolePermissionRequest request) {
        roleService.deleteRolePermission(request);
        return ApiResponse.<Void>builder().code(ResponseCode.SUCCESS.getCode()).build();
    }

    @PutMapping("/updateRolePermission")
    public ApiResponse<Role> updateRolePermission(@RequestBody final RolePermissionRequest request) {
        return ApiResponse.<Role>builder()
                .result(roleService.updateRolePermission(request))
                .code(ResponseCode.SUCCESS.getCode())
                .build();
    }
}
