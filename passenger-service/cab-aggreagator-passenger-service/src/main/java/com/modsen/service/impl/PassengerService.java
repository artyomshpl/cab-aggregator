package com.modsen.service.impl;

import com.modsen.model.Passenger;

import java.util.List;

public interface PassengerService {
    Passenger savePassenger(Passenger passenger);
    List<Passenger> getAllPassengers();
    Passenger getPassengerById(Long id);
    Passenger getPassengerByName(String name);
    void deletePassenger(Long id);
}
