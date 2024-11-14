package com.modsen.passenger.controller;

import com.modsen.passenger.dto.PassengerRequestDto;
import com.modsen.passenger.dto.PassengerResponseDto;
import com.modsen.passenger.dto.PageResponseDto;
import com.modsen.passenger.dto.PassengerListResponseDto;
import com.modsen.passenger.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerResponseDto> savePassenger(@RequestBody PassengerRequestDto passengerRequestDto) {
        PassengerResponseDto savedPassengerDto = passengerService.savePassenger(passengerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPassengerDto);
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<PassengerResponseDto>> getAllPassengers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponseDto<PassengerResponseDto> passengers = passengerService.getAllPassengers(page, size);
        return ResponseEntity.ok(passengers);
    }

    @GetMapping("/name")
    public ResponseEntity<PassengerListResponseDto> getPassengersByName(@RequestParam("name") String name) {
        PassengerListResponseDto passengers = passengerService.getPassengersByName(name);
        return ResponseEntity.ok(passengers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> getPassengerById(@PathVariable Long id) {
        PassengerResponseDto passengerDto = passengerService.getPassengerById(id);
        return ResponseEntity.ok(passengerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
