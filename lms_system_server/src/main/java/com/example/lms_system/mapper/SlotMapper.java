package com.example.lms_system.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.lms_system.dto.request.SlotRequest;
import com.example.lms_system.dto.response.SlotResponse;
import com.example.lms_system.entity.Slot;

@Mapper(componentModel = "spring")
public interface SlotMapper {
    @Mapping(target = "slotId", ignore = true)
    Slot toSlot(SlotRequest request);
    
    SlotResponse toSlotResponse(Slot slot);
}
