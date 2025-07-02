package com.example.schedule_service.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subjec {
    String subjectCode; // SWP

    String subjectName; // softwareprojet

    boolean status;
}
