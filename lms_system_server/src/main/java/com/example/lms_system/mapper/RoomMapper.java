package com.example.lms_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.lms_system.dto.request.RoomRequest;
import com.example.lms_system.dto.response.RoomResponse;
import com.example.lms_system.entity.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    @Mapping(target = "roomId", ignore = true)
    Room toRoom(RoomRequest request);

    RoomResponse toRoomResponse(Room room);
}
