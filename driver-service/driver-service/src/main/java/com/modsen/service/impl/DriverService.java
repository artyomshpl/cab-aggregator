package com.modsen.service.impl;

import com.modsen.model.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {
    List<Driver> getAllDrivers();
    Optional<Driver> getDriverById(String id);
    Driver saveDriver(Driver driver);
    void deleteDriver(String id);
}