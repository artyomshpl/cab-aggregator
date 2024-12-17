package com.modsen.rides.service;

import com.modsen.rides.dto.DriverDto;
import com.modsen.rides.dto.PassengerDto;
import com.modsen.rides.dto.RideDto;

import java.util.List;

public interface RideService {
    void processNewPassenger(PassengerDto passengerDto);
    void processFreeDrivers(List<DriverDto> drivers);
    void updateRideRating(RideDto rideDto);
}
