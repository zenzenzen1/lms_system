package com.example.schedule_service.mapper;

import org.mapstruct.Mapper;

import com.example.schedule_service.entity.CourseStudent;
import com.example.schedule_service.entity.dto.response.CourseStudentResponse;

@Mapper(componentModel = "spring")
public interface CourseStudentMapper {
    CourseStudentResponse toCourseStudentResponse(CourseStudent courseStudent);
}
