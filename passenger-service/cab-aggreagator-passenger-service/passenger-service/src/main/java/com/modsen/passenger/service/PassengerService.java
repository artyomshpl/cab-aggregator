package com.modsen.passenger.service;

import com.modsen.passenger.dto.PassengerRequest;
import com.modsen.passenger.dto.PassengerResponse;
import com.modsen.passenger.dto.PageResponse;
import com.modsen.passenger.dto.PassengerListResponse;

public interface PassengerService {
    PassengerResponse savePassenger(PassengerRequest passengerRequest);
    PageResponse<PassengerResponse> getAllPassengers(int page, int size);
    PassengerResponse getPassengerById(Long id);
    PassengerListResponse getPassengersByName(String name);
    void deletePassenger(Long id);
    PassengerResponse newRide(PassengerRequest passengerRequest);
    void updatePassenger(PassengerResponse passengerResponse);
}

