package com.modsen.rides.service.impl;

import com.modsen.rides.dto.DistanceAndDurationDto;
import com.modsen.rides.dto.DriverDto;
import com.modsen.rides.dto.PassengerDto;
import com.modsen.rides.dto.RideDto;
import com.modsen.rides.service.DirectionService;
import com.modsen.rides.service.RideCalculationService;
import com.modsen.rides.util.DistanceAndDurationParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RideCalculationServiceImpl implements RideCalculationService {
    private final DirectionService directionService;
    private final DistanceAndDurationParser distanceAndDurationParser;

    @Value("${price.per.kilometer:1.0}")
    private BigDecimal pricePerKilometer;

    public RideDto calculateRideDetails(PassengerDto passenger, DriverDto driver) {
        DistanceAndDurationDto toPassenger = directionService.getDirections(driver.location(), passenger.startPoint());
        DistanceAndDurationDto toDestination = directionService.getDirections(passenger.startPoint(), passenger.finalPoint());

        Long waitTime = distanceAndDurationParser.parseDurationToSeconds(toPassenger.duration());
        Long travelTime = distanceAndDurationParser.parseDurationToSeconds(toDestination.duration());
        Double routeLength = distanceAndDurationParser.parseDistanceToKilometers(toDestination.distance());
        BigDecimal price = calculatePrice(routeLength);

        return new RideDto(
                null,
                passenger.id().toString(),
                driver.id(),
                waitTime,
                travelTime,
                routeLength,
                price,
                null
        );
    }

    private BigDecimal calculatePrice(Double routeLength) {
        return BigDecimal.valueOf(routeLength).multiply(pricePerKilometer);
    }
}
