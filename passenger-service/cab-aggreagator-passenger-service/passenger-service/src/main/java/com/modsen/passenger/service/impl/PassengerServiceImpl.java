package com.modsen.passenger.service.impl;

import com.modsen.passenger.dto.*;
import com.modsen.passenger.exception.ResourceNotFoundException;
import com.modsen.passenger.kafka.KafkaProducer;
import com.modsen.passenger.mapper.PassengerMapper;
import com.modsen.passenger.model.Passenger;
import com.modsen.passenger.repository.PassengerRepository;
import com.modsen.passenger.service.PassengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final KafkaProducer kafkaProducer;
    private CompletableFuture<List<RideDto>> ridesFuture = new CompletableFuture<>();

    @Override
    public PassengerResponse savePassenger(PassengerRequest passengerRequest) {
        Passenger passenger = passengerMapper.toEntity(passengerRequest);
        log.info("Saving passenger: {}", passenger);
        Passenger savedPassenger = passengerRepository.save(passenger);
        PassengerResponse passengerResponse = passengerMapper.toDto(savedPassenger);
        kafkaProducer.sendNewPassenger(passengerResponse);
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

    @Override
    public PassengerResponse newRide(PassengerRequest passengerRequest) {
        Passenger passenger = passengerMapper.toEntity(passengerRequest);
        Passenger existingPassenger = passengerRepository.findByNameAndEmail(passenger.getName(), passenger.getEmail())
                .orElse(null);

        if (existingPassenger != null) {
            existingPassenger.setStartPoint(passenger.getStartPoint());
            existingPassenger.setFinalPoint(passenger.getFinalPoint());
            existingPassenger.setStatus(passenger.getStatus());
            passenger = passengerRepository.save(existingPassenger);
        } else {
            passenger = passengerRepository.save(passenger);
        }

        PassengerResponse passengerResponse = passengerMapper.toDto(passenger);
        kafkaProducer.sendNewPassenger(passengerResponse);
        return passengerResponse;
    }

    @Override
    public void updatePassenger(PassengerResponse passengerResponse) {
        Passenger passenger = passengerRepository.findById(passengerResponse.id())
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + passengerResponse.id()));

        passenger.setName(passengerResponse.name());
        passenger.setEmail(passengerResponse.email());
        passenger.setStartPoint(passengerResponse.startPoint());
        passenger.setFinalPoint(passengerResponse.finalPoint());
        passenger.setStatus(passengerResponse.status());

        passengerRepository.save(passenger);
    }

    @Override
    public CompletableFuture<List<RideDto>> requestRides(PassengerIdDto passengerIdDto) {
        ridesFuture = new CompletableFuture<>();
        kafkaProducer.sendRequestRides(passengerIdDto);
        return ridesFuture;
    }

    @Override
    public void rateRide(RideDto rideDto) {
        kafkaProducer.sendUpdateRideRating(rideDto);
    }

    @Override
    public void receiveRides(List<RideDto> rides) {
        ridesFuture.complete(rides);
    }
}
