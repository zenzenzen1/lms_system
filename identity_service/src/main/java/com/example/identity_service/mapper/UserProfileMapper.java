package com.example.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserProfileCreationRequest;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "active", ignore = true)
    UserProfileCreationRequest toUserProfileCreationRequest(UserCreationRequest userCreationRequest);
}
