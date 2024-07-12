package com.example.lms_system.controller;

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

import com.example.lms_system.entity.Room;
import com.example.lms_system.service.RoomService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = {"/rooms", "/rooms/"})
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/all")
    public ResponseEntity<Page<Room>> getRooms(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Room> roomPage = roomService.getRooms(page, size);
        HttpHeaders headers = new HttpHeaders();

        headers.add("Room-page-number", String.valueOf(roomPage.getNumber()));
        headers.add("Room-page-size", String.valueOf(roomPage.getSize()));
        return ResponseEntity.ok().headers(headers).body(roomPage);
    }

    @GetMapping("/details")
    public ResponseEntity<Room> getRoomDetails(@RequestParam Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Room> addRoom(@RequestParam String roomNumber) {
        Room room = Room.builder().roomNumber(roomNumber).build();
        roomService.saveRoom(room);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestParam String roomNumber) {
        Room room = roomService.findById(id);
        if (room == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        room.setRoomNumber(roomNumber);
        roomService.saveRoom(room);
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        Room room = roomService.findById(id);
        if (room == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
