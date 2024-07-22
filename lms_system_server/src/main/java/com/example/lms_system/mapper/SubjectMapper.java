package com.example.lms_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.lms_system.dto.request.SubjectRequest;
import com.example.lms_system.dto.response.SubjectResponse;
import com.example.lms_system.entity.Subject;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    
    @Mapping(target = "subjectCode", ignore = true)
    Subject toSubject(SubjectRequest request);

    SubjectResponse toSubjectResponse(Subject subject);
}
