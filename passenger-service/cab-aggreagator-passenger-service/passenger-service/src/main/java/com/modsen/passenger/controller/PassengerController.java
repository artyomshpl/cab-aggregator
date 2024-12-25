package com.modsen.passenger.controller;

import com.modsen.passenger.dto.*;
import com.modsen.passenger.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/passengers")
@RequiredArgsConstructor
public class PassengerController {
    private final PassengerService passengerService;

    @PostMapping
    public ResponseEntity<PassengerResponse> savePassenger(@RequestBody PassengerRequest passengerRequest) {
        PassengerResponse savedPassengerDto = passengerService.savePassenger(passengerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedPassengerDto);
    }

    @GetMapping
    public ResponseEntity<PageResponse<PassengerResponse>> getAllPassengers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PageResponse<PassengerResponse> passengers = passengerService.getAllPassengers(page, size);
        return ResponseEntity.ok()
                .body(passengers);
    }

    @GetMapping("/name")
    public ResponseEntity<PassengerListResponse> getPassengersByName(@RequestParam("name") String name) {
        PassengerListResponse passengers = passengerService.getPassengersByName(name);
        return ResponseEntity.ok()
                .body(passengers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassengerById(@PathVariable Long id) {
        PassengerResponse passengerDto = passengerService.getPassengerById(id);
        return ResponseEntity.ok()
                .body(passengerDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/newRide")
    public ResponseEntity<PassengerResponse> newRide(@RequestBody PassengerRequest passengerRequest) {
        PassengerResponse passengerResponse = passengerService.newRide(passengerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(passengerResponse);
    }

    @PostMapping("/rides")
    public ResponseEntity<Page<RideDto>> getRides(@RequestParam String passengerId,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        Page<RideDto> rides = passengerService.requestRides(passengerId, page, size);
        return ResponseEntity.ok(rides);
    }

    @PostMapping("/rateRide")
    public ResponseEntity<Void> rateRide(@RequestBody RideDto rideDto) {
        passengerService.rateRide(rideDto);
        return ResponseEntity.ok().build();
    }
}
