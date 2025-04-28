package com.modsen.rides.controller;

import com.modsen.rides.dto.DistanceAndDurationDto;
import com.modsen.rides.service.DirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/v1/directions")
@RequiredArgsConstructor
@Validated
public class DirectionsController {
    private final DirectionService directionsService;

    @GetMapping
    public ResponseEntity<DistanceAndDurationDto> getDirections(
            @RequestParam @NotBlank(message = "Origin can not be empty") String origin,
            @RequestParam @NotBlank(message = "Destination can not be empty") String destination) {
        DistanceAndDurationDto result = directionsService.getDirections(origin, destination);
        return ResponseEntity.ok(result);
    }
}