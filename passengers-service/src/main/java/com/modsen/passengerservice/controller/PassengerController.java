package com.modsen.passengerservice.controller;

import com.modsen.passengerservice.dto.PassengerDto;
import com.modsen.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;

    @PostMapping()
    public ResponseEntity<PassengerDto> savePassenger(@RequestBody PassengerDto passengerDto) {
        PassengerDto savedPassengerDto = passengerService.savePassenger(passengerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPassengerDto);
    }

    @GetMapping
    public ResponseEntity<Page<PassengerDto>> getAllPassengers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PassengerDto> passengers = passengerService.getAllPassengers(pageable);
        return ResponseEntity.ok(passengers);
    }


    @GetMapping("/name")
    public ResponseEntity<List<PassengerDto>> getPassengersByName(@RequestParam("name") String name) {
        List<PassengerDto> passengers = passengerService.getPassengersByName(name);
        return ResponseEntity.ok(passengers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerDto> getPassengerById(@PathVariable Long id) {
        PassengerDto passengerDto = passengerService.getPassengerById(id);
        return ResponseEntity.ok(passengerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }
}
