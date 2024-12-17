package com.modsen.passenger.service;

import com.modsen.passenger.dto.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PassengerService {
    PassengerResponse savePassenger(PassengerRequest passengerRequest);
    PageResponse<PassengerResponse> getAllPassengers(int page, int size);
    PassengerResponse getPassengerById(Long id);
    PassengerListResponse getPassengersByName(String name);
    void deletePassenger(Long id);
    PassengerResponse newRide(PassengerRequest passengerRequest);
    void updatePassenger(PassengerResponse passengerResponse);
    void rateRide(RideDto rideDto);
    void receiveRides(List<RideDto> rides);
    CompletableFuture<List<RideDto>> requestRides(PassengerIdDto passengerIdDto);
}

