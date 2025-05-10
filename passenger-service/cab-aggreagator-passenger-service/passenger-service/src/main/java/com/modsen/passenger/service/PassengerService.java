package com.modsen.passenger.service;

import com.modsen.passenger.dto.*;
import org.springframework.data.domain.Page;

public interface PassengerService {
    PassengerResponse savePassenger(PassengerRequest passengerRequest);
    PageResponse<PassengerResponse> getAllPassengers(int page, int size);
    PassengerResponse getPassengerById(Long id);
    PassengerListResponse getPassengersByName(String name);
    void deletePassenger(Long id);
    PassengerResponse newRide(PassengerRequest passengerRequest);
    void updatePassenger(PassengerResponse passengerResponse);
    void rateRide(RideDto rideDto);
    Page<RideDto> requestRides(String passengerId, int page, int size);
}

