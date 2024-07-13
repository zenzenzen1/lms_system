package com.example.lms_system.service;

import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.lms_system.dto.response.SlotResponse;
import com.example.lms_system.entity.Slot;
import com.example.lms_system.repository.SlotRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SlotService {

    private final SlotRepository slotRepository;

    public void saveSlot(Slot slot) {
        slotRepository.save(slot);
    }

    public void deleteById(Long id) {
        slotRepository.deleteById(id);
    }

    public Slot findById(Long id) {
        return slotRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Slot not found."));
    }

    public Page<Slot> getSlots(int page, int size) {
        List<Slot> list = slotRepository.findAll();

        Pageable pageRequest = createPageRequest(page, size);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), list.size());

        List<Slot> pageContent = list.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, list.size());
    }

    private Pageable createPageRequest(int page, int size) {
        return PageRequest.of(page, size);
    }

    public List<Slot> getSlotListFromPage(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        Page<Slot> slotList = slotRepository.findAll(pageRequest);

        return slotList.hasContent() ? slotList.getContent() : Collections.emptyList();
    }

    public List<SlotResponse> getAllSlots() {
        return slotRepository.findAll().stream()
                .map(t -> SlotResponse.builder()
                        .slotId(t.getSlotId())
                        .startTime(t.getStartTime())
                        .endTime(t.getEndTime())
                        .build())
                .toList();
    }
}
