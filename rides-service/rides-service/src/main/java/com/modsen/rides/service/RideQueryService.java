package com.modsen.rides.service;

import com.modsen.rides.dto.RideDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RideQueryService {
    RideDto getLatestRide();
    Page<RideDto> getAllRides(Pageable pageable);
    List<RideDto> getRidesByPassengerId(String passengerId);
}
