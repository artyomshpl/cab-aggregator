package com.modsen.rides.service.impl;

import com.modsen.rides.dto.RideDto;
import com.modsen.rides.mapper.RideMapper;
import com.modsen.rides.model.Ride;
import com.modsen.rides.repository.RideRepository;
import com.modsen.rides.service.RideQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RideQueryServiceImpl implements RideQueryService {

    private final RideRepository rideRepository;
    private final RideMapper rideMapper;

    @Override
    @Transactional
    public RideDto getLatestRide() {
        Ride latestRide = rideRepository.findTopByOrderByIdDesc();
        if (latestRide == null) {
            throw new NoSuchElementException("Latest ride not found");
        }
        return rideMapper.toDto(latestRide);
    }

    @Override
    @Transactional
    public Page<RideDto> getAllRides(Pageable pageable) {
        Page<Ride> rides = rideRepository.findAll(pageable);
        return rides.map(rideMapper::toDto);
    }

    @Override
    @Transactional
    public Page<RideDto> getRidesByPassengerId(String passengerId, Pageable pageable) {
        Page<Ride> rides = rideRepository.findByPassengerId(passengerId, pageable);
        return rides.map(rideMapper::toDto);
    }
}
