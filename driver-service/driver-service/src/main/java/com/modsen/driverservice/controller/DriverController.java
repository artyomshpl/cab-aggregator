package com.modsen.driverservice.controller;

import com.modsen.driverservice.dto.DriverDTO;
import com.modsen.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @GetMapping
    public ResponseEntity<Page<DriverDTO>> getAllDrivers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DriverDTO> drivers = driverService.getAllDrivers(pageable);
        return ResponseEntity.ok(drivers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDTO> getDriverById(@PathVariable String id) {
        DriverDTO driverDTO = driverService.getDriverById(id);
        return ResponseEntity.ok(driverDTO);
    }

    @PostMapping
    public ResponseEntity<DriverDTO> saveDriver(@RequestBody DriverDTO driverDTO) {
        DriverDTO savedDriverDTO = driverService.saveDriver(driverDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDriverDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable String id) {
        driverService.deleteDriver(id);
        return ResponseEntity.noContent().build();
    }
}
