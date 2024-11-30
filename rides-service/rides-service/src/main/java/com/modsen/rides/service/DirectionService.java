package com.modsen.rides.service;


import com.modsen.rides.dto.DistanceAndDurationDto;

public interface DirectionService {
    DistanceAndDurationDto getDirections(String origin, String destination);
}
