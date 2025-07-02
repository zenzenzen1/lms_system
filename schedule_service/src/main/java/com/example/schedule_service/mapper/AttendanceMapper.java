package com.example.schedule_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.schedule_service.entity.Attendance;
import com.example.schedule_service.entity.dto.request.AttendanceRequest;
import com.example.schedule_service.entity.dto.response.AttendanceResponse;


@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    @Mapping(target = "student", ignore = true)
    @Mapping(target = "schedule", ignore = true)
    @Mapping(target = "attendanceId", ignore = true)
    // Attendance toAttendance(AttendanceRequest attendanceRequest);
    void updateAttendance(@MappingTarget Attendance attendance, AttendanceRequest request);
    
    @Mapping(target = "schedule", ignore = true)
    AttendanceResponse toAttendanceResponse(Attendance attendance);
}
