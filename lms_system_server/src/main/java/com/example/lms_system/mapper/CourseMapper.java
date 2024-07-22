package com.example.lms_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.lms_system.dto.request.CourseRequest;
import com.example.lms_system.dto.response.CourseResponse;
import com.example.lms_system.entity.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(target = "courseStudents", ignore = true)
    @Mapping(target = "courseId", ignore = true)
    Course toCourse(CourseRequest request);

    CourseResponse toCourseResponse(Course course);
}
