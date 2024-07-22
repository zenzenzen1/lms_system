package com.example.lms_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.lms_system.dto.request.AttendanceRequest;
import com.example.lms_system.dto.response.AttendanceResponse;
import com.example.lms_system.entity.Attendance;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {
    @Mapping(target = "attendanceId", ignore = true)
    Attendance toAttendance(AttendanceRequest request);

    AttendanceResponse toAttendanceResponse(Attendance attendance);
}
