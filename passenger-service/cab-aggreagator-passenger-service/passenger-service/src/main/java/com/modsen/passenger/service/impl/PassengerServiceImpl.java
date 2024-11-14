package com.modsen.passenger.service.impl;

import com.modsen.passenger.dto.PassengerRequestDto;
import com.modsen.passenger.dto.PassengerResponseDto;
import com.modsen.passenger.dto.PageResponseDto;
import com.modsen.passenger.dto.PassengerListResponseDto;
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
    public PassengerResponseDto savePassenger(PassengerRequestDto passengerRequestDto) {
        Passenger passenger = passengerMapper.toEntity(passengerRequestDto);
        log.info("Saving passenger: {}", passenger);
        Passenger savedPassenger = passengerRepository.save(passenger);
        return passengerMapper.toDto(savedPassenger);
    }

    @Override
    public PageResponseDto<PassengerResponseDto> getAllPassengers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Passenger> passengerPage = passengerRepository.findAll(pageable);
        List<PassengerResponseDto> passengerDtos = passengerPage.getContent().stream()
                .map(passengerMapper::toDto)
                .collect(Collectors.toList());
        return new PageResponseDto<>(passengerDtos, passengerPage.getTotalPages(), passengerPage.getTotalElements());
    }

    @Override
    public PassengerResponseDto getPassengerById(Long id) {
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
    public PassengerListResponseDto getPassengersByName(String name) {
        List<Passenger> passengers = passengerRepository.findByName(name);
        if (passengers.isEmpty()) {
            return new PassengerListResponseDto(List.of());
        }
        List<PassengerResponseDto> passengerDtos = passengers.stream()
                .map(passengerMapper::toDto)
                .collect(Collectors.toList());
        return new PassengerListResponseDto(passengerDtos);
    }
}
