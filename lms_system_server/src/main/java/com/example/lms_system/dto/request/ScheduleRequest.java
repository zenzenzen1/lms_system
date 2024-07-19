package com.example.lms_system.dto.request;

import java.util.Set;

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
    String subjectCode;
    Set<String> studentIds;
}
