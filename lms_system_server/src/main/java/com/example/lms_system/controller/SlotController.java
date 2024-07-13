package com.example.lms_system.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.lms_system.dto.response.ApiResponse;
import com.example.lms_system.dto.response.SlotResponse;
import com.example.lms_system.entity.Slot;
import com.example.lms_system.service.SlotService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = {"/slots", "/slots/"})
public class SlotController {

    private final SlotService slotService;

    @GetMapping("/all")
    public ResponseEntity<Page<Slot>> getSlots(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Slot> slotPage = slotService.getSlots(page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Slot-page-number", String.valueOf(slotPage.getNumber()));
        headers.add("Slot-page-size", String.valueOf(slotPage.getSize()));
        return ResponseEntity.ok().headers(headers).body(slotPage);
    }

    @GetMapping("/details")
    public ResponseEntity<Slot> getSlotDetails(@RequestParam Long id) {
        return ResponseEntity.ok(slotService.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Slot> addSlot(@RequestParam LocalTime startTime, @RequestParam LocalTime endTime) {
        Slot slot = Slot.builder().startTime(startTime).endTime(endTime).build();
        return new ResponseEntity<>(slot, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Slot> updateSlot(
            @PathVariable Long id, @RequestParam LocalTime startTime, @RequestParam LocalTime endTime) {
        Slot slot = slotService.findById(id);
        if (slot == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        slot.setStartTime(startTime);
        slot.setEndTime(endTime);
        slotService.saveSlot(slot);
        return new ResponseEntity<>(slot, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSlot(@PathVariable Long id) {
        Slot slot = slotService.findById(id);
        if (slot == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        slotService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ApiResponse<List<SlotResponse>> getAllSlots() {
        return ApiResponse.<List<SlotResponse>>builder()
                .result(slotService.getAllSlots())
                .build();
    }
}
