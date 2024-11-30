package com.modsen.passenger.service.impl;

import com.modsen.passenger.dto.PassengerRequest;
import com.modsen.passenger.dto.PassengerResponse;
import com.modsen.passenger.dto.PageResponse;
import com.modsen.passenger.dto.PassengerListResponse;
import com.modsen.passenger.exception.ResourceNotFoundException;
import com.modsen.passenger.mapper.PassengerMapper;
import com.modsen.passenger.model.Passenger;
import com.modsen.passenger.repository.PassengerRepository;
import com.modsen.passenger.service.PassengerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public PassengerResponse savePassenger(PassengerRequest passengerRequest) {
        Passenger passenger = passengerMapper.toEntity(passengerRequest);
        log.info("Saving passenger: {}", passenger);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return passengerMapper.toDto(savedPassenger);
    }

    @Override
    public PageResponse<PassengerResponse> getAllPassengers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Passenger> passengerPage = passengerRepository.findAll(pageable);
        List<PassengerResponse> passengerDtos = passengerPage.getContent().stream()
                .map(passengerMapper::toDto)
                .collect(Collectors.toList());
        return PageResponse.<PassengerResponse>builder()
                .content(passengerDtos)
                .totalPages(passengerPage.getTotalPages())
                .totalElements(passengerPage.getTotalElements())
                .build();
    }

    @Override
    public PassengerResponse getPassengerById(Long id) {
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
    public PassengerListResponse getPassengersByName(String name) {
        List<Passenger> passengers = passengerRepository.findByName(name);
        List<PassengerResponse> passengerDtos = passengers.stream()
                .map(passengerMapper::toDto)
                .collect(Collectors.toList());
        return new PassengerListResponse(passengerDtos);
    }

}
