package com.example.lms_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.lms_system.dto.request.SemesterRequest;
import com.example.lms_system.dto.response.SemesterResponse;
import com.example.lms_system.entity.Semester;

@Mapper(componentModel = "spring")
public interface SemesterMapper {
    @Mapping(target = "semesterCode", ignore = true)
    Semester toSemester(SemesterRequest request);

    SemesterResponse toSemesterResponse(Semester semester);
}
