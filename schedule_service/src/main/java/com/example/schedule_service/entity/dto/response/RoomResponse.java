package com.example.schedule_service.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomResponse {
    Long roomId;
    String roomNumber;
}
