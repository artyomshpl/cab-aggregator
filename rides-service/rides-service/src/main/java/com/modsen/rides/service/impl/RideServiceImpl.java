package com.modsen.rides.service.impl;

import com.modsen.rides.dto.DistanceAndDurationDto;
import com.modsen.rides.dto.DriverDto;
import com.modsen.rides.dto.PassengerDto;
import com.modsen.rides.dto.RideDto;
import com.modsen.rides.kafka.KafkaProducer;
import com.modsen.rides.mapper.RideMapper;
import com.modsen.rides.model.Ride;
import com.modsen.rides.repository.RideRepository;
import com.modsen.rides.service.DirectionService;
import com.modsen.rides.service.RideCalculationService;
import com.modsen.rides.service.RideService;
import com.modsen.rides.util.DistanceAndDurationParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final DirectionService directionService;
    private final KafkaProducer kafkaProducer;
    private final RideCalculationService rideCalculationService;
    private final DistanceAndDurationParser distanceAndDurationParser;
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;

    private PassengerDto currentPassenger;
    private DriverDto currentDriver;
    private RideDto currentRide;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

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
                currentDriver = updateDriverStatus(closestDriver, "assigned");
                currentPassenger = updatePassengerStatus(currentPassenger, "assigned");

                kafkaProducer.sendDriverUpdates(currentDriver);
                kafkaProducer.sendPassengerUpdates(currentPassenger);

                currentRide = rideCalculationService.calculateRideDetails(currentPassenger, currentDriver);

                scheduleStatusUpdates();
            }
        }
    }

    private void scheduleStatusUpdates() {
        scheduler.schedule(() -> {
            currentDriver = updateDriverStatus(currentDriver, "arrived");
            currentPassenger = updatePassengerStatus(currentPassenger, "riding");

            kafkaProducer.sendDriverUpdates(currentDriver);
            kafkaProducer.sendPassengerUpdates(currentPassenger);

            scheduler.schedule(() -> {
                currentDriver = updateDriverStatus(currentDriver, "free");
                currentPassenger = updatePassengerStatus(currentPassenger, "completed");

                kafkaProducer.sendDriverUpdates(currentDriver);
                kafkaProducer.sendPassengerUpdates(currentPassenger);

                saveRideDetails(currentRide);
            }, currentRide.travelTime(), TimeUnit.SECONDS);
        }, currentRide.waitTime(), TimeUnit.SECONDS);
    }

    private void saveRideDetails(RideDto ride) {
        Ride rideEntity = rideMapper.toEntity(ride);
        rideRepository.save(rideEntity);
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
        return distanceAndDurationParser.parseDistanceToKilometers(distanceAndDuration.distance());
    }

    private DriverDto updateDriverStatus(DriverDto driver, String status) {
        return new DriverDto(driver.id(), driver.name(), driver.licenseNumber(), driver.phoneNumber(), driver.location(), status, driver.activityState());
    }

    private PassengerDto updatePassengerStatus(PassengerDto passenger, String status) {
        return new PassengerDto(passenger.id(), passenger.name(), passenger.email(), passenger.startPoint(), passenger.finalPoint(), status);
    }
}
