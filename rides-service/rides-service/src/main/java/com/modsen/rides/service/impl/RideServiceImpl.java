package com.modsen.rides.service.impl;

import com.modsen.rides.dto.DistanceAndDurationDto;
import com.modsen.rides.dto.DriverDto;
import com.modsen.rides.dto.PassengerDto;
import com.modsen.rides.kafka.KafkaProducer;
import com.modsen.rides.service.DirectionService;
import com.modsen.rides.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final DirectionService directionService;
    private final KafkaProducer kafkaProducer;

    private PassengerDto currentPassenger;

    @Override
    public void processNewPassenger(PassengerDto passenger) {
        currentPassenger = passenger;
        kafkaProducer.sendNeedFreeDrivers("Need free drivers for passenger: " + passenger.id());
    }

    @Override
    public void processFreeDrivers(List<DriverDto> drivers) {
        if (currentPassenger != null) {
            DriverDto closestDriver = findClosestDriver(currentPassenger, drivers);
            if (closestDriver != null) {
                closestDriver = updateDriverStatus(closestDriver, "assigned");
                currentPassenger = updatePassengerStatus(currentPassenger, "assigned");

                kafkaProducer.sendDriverUpdates(closestDriver);
                kafkaProducer.sendPassengerUpdates(currentPassenger);
            }
        }
    }

    private DriverDto findClosestDriver(PassengerDto passenger, List<DriverDto> drivers) {
        return drivers.stream()
                .min((driver1, driver2) -> {
                    double distance1 = calculateDistance(passenger.startPoint(), driver1.location());
                    double distance2 = calculateDistance(passenger.startPoint(), driver2.location());
                    return Double.compare(distance1, distance2);
                })
                .orElse(null);
    }

    private double calculateDistance(String startPoint, String location) {
        DistanceAndDurationDto distanceAndDuration = directionService.getDirections(startPoint, location);
        return Double.parseDouble(distanceAndDuration.distance().replaceAll("[^\\d.]", ""));
    }

    private DriverDto updateDriverStatus(DriverDto driver, String status) {
        return new DriverDto(driver.id(), driver.name(), driver.licenseNumber(), driver.phoneNumber(), driver.location(), status, driver.activityState());
    }

    private PassengerDto updatePassengerStatus(PassengerDto passenger, String status) {
        return new PassengerDto(passenger.id(), passenger.name(), passenger.email(), passenger.startPoint(), passenger.finalPoint(), status);
    }
}
