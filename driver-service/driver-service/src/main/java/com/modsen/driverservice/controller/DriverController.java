package com.modsen.driverservice.controller;

import com.modsen.driverservice.dto.DriverDTO;
import com.modsen.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @GetMapping
    public Page<DriverDTO> getAllDrivers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return driverService.getAllDrivers(pageable);
    }

    @GetMapping("/{id}")
    public DriverDTO getDriverById(@PathVariable String id) {
        return driverService.getDriverById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO saveDriver(@RequestBody DriverDTO driverDTO) {
        return driverService.saveDriver(driverDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable String id) {
        driverService.deleteDriver(id);
    }
}