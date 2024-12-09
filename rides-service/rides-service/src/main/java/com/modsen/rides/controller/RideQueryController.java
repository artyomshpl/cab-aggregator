package com.modsen.rides.controller;

import com.modsen.rides.dto.RideDto;
import com.modsen.rides.service.RideQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/rides")
@RequiredArgsConstructor
public class RideQueryController {

    private final RideQueryService rideQueryService;

    @GetMapping("/latest")
    public ResponseEntity<RideDto> getLatestRide() {
        Optional<RideDto> latestRide = rideQueryService.getLatestRide();
        return latestRide.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping
    public ResponseEntity<Page<RideDto>> getAllRides(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RideDto> rides = rideQueryService.getAllRides(pageable);
        return ResponseEntity.ok(rides);
    }
}
