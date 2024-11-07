package com.modsen.driverservice.controller;

import com.modsen.driverservice.dto.DriverDTO;
import com.modsen.driverservice.exception.DriverNotFoundException;
import com.modsen.driverservice.mapper.DriverMapper;
import com.modsen.driverservice.model.Driver;
import com.modsen.driverservice.service.impl.DriverService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/drivers")
@AllArgsConstructor
public class DriverController {
    private final DriverService driverService;
    private final DriverMapper driverMapper;

    @GetMapping
    public List<DriverDTO> getAllDrivers() {
        return driverService.getAllDrivers().stream()
                .map(driverMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public DriverDTO getDriverById(@PathVariable String id) {
        Optional<Driver> driver = driverService.getDriverById(id);
        if (driver.isPresent()) {
            return driverMapper.toDTO(driver.get());
        } else {
            throw new DriverNotFoundException("Driver not found with id: " + id);
        }
    }

    @PostMapping
    public DriverDTO saveDriver(@RequestBody DriverDTO driverDTO) {
        Driver driver = driverMapper.toEntity(driverDTO);
        return driverMapper.toDTO(driverService.saveDriver(driver));
    }

    @DeleteMapping("/{id}")
    public void deleteDriver(@PathVariable String id) {
        driverService.deleteDriver(id);
    }
}
