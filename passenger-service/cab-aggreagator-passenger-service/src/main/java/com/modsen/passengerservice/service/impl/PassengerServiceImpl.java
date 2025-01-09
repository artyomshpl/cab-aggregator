package com.modsen.passengerservice.service.impl;

import com.modsen.passengerservice.dto.PassengerDto;
import com.modsen.passengerservice.exception.ResourceNotFoundException;
import com.modsen.passengerservice.mapper.PassengerMapper;
import com.modsen.passengerservice.model.Passenger;
import com.modsen.passengerservice.repository.PassengerRepository;
import com.modsen.passengerservice.service.PassengerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public PassengerDto savePassenger(PassengerDto passengerDto) {
        Passenger passenger = passengerMapper.toEntity(passengerDto);
        System.out.println(passenger);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return passengerMapper.toDto(savedPassenger);
    }

    @Override
    public Page<PassengerDto> getAllPassengers(Pageable pageable) {
        return passengerRepository.findAll(pageable).map(passengerMapper::toDto);
    }

    @Override
    public PassengerDto getPassengerById(Long id) {
        return passengerRepository.findById(id)
                .map(passengerMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + id));
    }

    @Override
    public void deletePassenger(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Passenger not found with id: " + id);
        }
        passengerRepository.deleteById(id);
    }

    @Override
    public List<PassengerDto> getPassengersByName(String name) {
        List<Passenger> passengers = passengerRepository.findByName(name);
        if (passengers.isEmpty()) {
            throw new ResourceNotFoundException("No passengers found with name: " + name);
        }
        return passengers.stream()
                .map(passengerMapper::toDto)
                .collect(Collectors.toList());
    }

}
