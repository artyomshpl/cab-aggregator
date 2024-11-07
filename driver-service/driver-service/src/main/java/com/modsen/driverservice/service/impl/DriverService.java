package com.modsen.driverservice.service.impl;

import com.modsen.driverservice.model.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {
    List<Driver> getAllDrivers();
    Optional<Driver> getDriverById(String id);
    Driver saveDriver(Driver driver);
    void deleteDriver(String id);
}