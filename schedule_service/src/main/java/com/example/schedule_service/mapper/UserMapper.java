package com.example.schedule_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.schedule_service.entity.User;
import com.example.schedule_service.entity.dto.request.UserRequest;
import com.example.schedule_service.entity.dto.response.UserResponse;


@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "courseStudents", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "_class", ignore = true)
    User toUser(UserRequest userRequest);

    UserResponse toUserResponse(User user);
}
