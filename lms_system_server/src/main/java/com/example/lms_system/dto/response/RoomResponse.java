package com.example.lms_system.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomResponse {
    Long roomId;
    String roomNumber;
}
