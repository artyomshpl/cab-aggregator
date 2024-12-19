package com.modsen.rides.service;

import com.modsen.rides.dto.RideDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RideQueryService {
    RideDto getLatestRide();
    Page<RideDto> getAllRides(Pageable pageable);
    Page<RideDto> getRidesByPassengerId(String passengerId, Pageable pageable);
}
