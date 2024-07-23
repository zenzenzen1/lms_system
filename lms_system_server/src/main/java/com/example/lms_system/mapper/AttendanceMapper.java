package com.example.lms_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.lms_system.dto.request.AttendanceRequest;
import com.example.lms_system.dto.response.AttendanceResponse;
import com.example.lms_system.entity.Attendance;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(target = "student", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    @Mapping(target = "attendanceId", ignore = true)
    Attendance toAttendance(AttendanceRequest attendanceRequest);

    @Mapping(target = "student", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    @Mapping(target = "attendanceId", ignore = true)
    void updateAttendance(@MappingTarget Attendance attendance, AttendanceRequest request);

    AttendanceResponse toAttendanceResponse(Attendance attendance);
}
