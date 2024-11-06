package com.modsen.service;

import com.modsen.model.Driver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.modsen.repository.DriverRepository;
import com.modsen.service.impl.DriverService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;

    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public Optional<Driver> getDriverById(String id) {
        return driverRepository.findById(id);
    }

    @Override
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public void deleteDriver(String id) {
        driverRepository.deleteById(id);
    }
}