package com.example.schedule_service.entity.dto.request;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ScheduleRequest {
    String semesterCode;
    Long slotId;
    Long roomId;
    // String subjectCode;
    Long courseId;
    
    @NotEmpty(message = "studentIds must not be empty")
    Set<String> studentIds;
}
