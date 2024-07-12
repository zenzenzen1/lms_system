package com.example.lms_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.lms_system.dto.request.UserRequest;
import com.example.lms_system.dto.response.UserResponse;
import com.example.lms_system.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "courseStudents", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toUser(UserRequest userRequest);

    UserResponse toUserResponse(User user);
}
