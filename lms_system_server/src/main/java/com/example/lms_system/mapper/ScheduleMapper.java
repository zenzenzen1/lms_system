package com.example.lms_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.lms_system.dto.request.ScheduleRequest;
import com.example.lms_system.dto.response.ScheduleResponse;
import com.example.lms_system.entity.Schedule;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    @Mapping(target = "scheduleId", ignore = true)
    Schedule toSchedule(ScheduleRequest request);

    ScheduleResponse toScheduleResponse(Schedule schedule);
}
