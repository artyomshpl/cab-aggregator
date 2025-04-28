package com.modsen.rides.service;

import com.modsen.rides.dto.DriverDto;
import com.modsen.rides.dto.PassengerDto;
import com.modsen.rides.dto.RideDto;

public interface RideCalculationService {
    RideDto calculateRideDetails(PassengerDto passenger, DriverDto driver);
}
