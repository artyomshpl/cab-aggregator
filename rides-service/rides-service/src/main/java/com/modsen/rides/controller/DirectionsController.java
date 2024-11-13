package com.modsen.rides.controller;

import com.modsen.rides.dto.DistanceAndDurationDto;
import com.modsen.rides.service.DirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class DirectionsController {
    private final DirectionService directionsService;

    @GetMapping("/directions")
    public ResponseEntity<DistanceAndDurationDto> getDirections(@RequestParam String origin, @RequestParam String destination) {
        DistanceAndDurationDto result = directionsService.getDirections(origin, destination);
        return ResponseEntity.ok(result);
    }
}