package com.modsen.passengerservice.repository;

import com.modsen.passengerservice.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByName(String name);
}