package com.modsen.passenger.repository;

import com.modsen.passenger.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    List<Passenger> findByName(String name);
    Optional<Passenger> findByNameAndEmail(String name, String email);
}