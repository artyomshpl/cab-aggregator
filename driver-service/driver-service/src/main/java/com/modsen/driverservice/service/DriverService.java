package com.modsen.driverservice.service;

import com.modsen.driverservice.dto.DriverDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriverService {
    Page<DriverDTO> getAllDrivers(Pageable pageable);
    DriverDTO getDriverById(String id);
    DriverDTO saveDriver(DriverDTO driverDTO);
    void deleteDriver(String id);
}
