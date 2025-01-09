package com.modsen.passengerservice.service;

import com.modsen.passengerservice.dto.PassengerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PassengerService {
    PassengerDto savePassenger(PassengerDto passengerDto);
    Page<PassengerDto> getAllPassengers(Pageable pageable);
    PassengerDto getPassengerById(Long id);
    List<PassengerDto> getPassengersByName(String name);
    void deletePassenger(Long id);
}
