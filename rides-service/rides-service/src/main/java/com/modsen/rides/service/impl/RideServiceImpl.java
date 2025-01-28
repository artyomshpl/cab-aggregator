package com.modsen.rides.service.impl;

import com.modsen.rides.dto.*;
import com.modsen.rides.kafka.KafkaProducer;
import com.modsen.rides.mapper.DriverRatingMapper;
import com.modsen.rides.mapper.PassengerRatingMapper;
import com.modsen.rides.mapper.RideMapper;
import com.modsen.rides.model.DriverRating;
import com.modsen.rides.model.PassengerRating;
import com.modsen.rides.model.Ride;
import com.modsen.rides.repository.DriverRatingRepository;
import com.modsen.rides.repository.PassengerRatingRepository;
import com.modsen.rides.repository.RideRepository;
import com.modsen.rides.service.DirectionService;
import com.modsen.rides.service.RideCalculationService;
import com.modsen.rides.service.RideService;
import com.modsen.rides.util.DistanceAndDurationParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final DirectionService directionService;
    private final KafkaProducer kafkaProducer;
    private final RideCalculationService rideCalculationService;
    private final DistanceAndDurationParser distanceAndDurationParser;
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final PassengerRatingRepository passengerRatingRepository;
    private final PassengerRatingMapper passengerRatingMapper;
    private final DriverRatingRepository driverRatingRepository;
    private final DriverRatingMapper driverRatingMapper;

    private PassengerDto currentPassenger;
    private DriverDto currentDriver;
    private RideDto currentRide;
    private final Set<String> rejectedDrivers = ConcurrentHashMap.newKeySet();

    @Override
    public void processNewPassenger(PassengerDto passenger) {
        currentPassenger = passenger;
        rejectedDrivers.clear();
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

                currentRide = rideCalculationService.calculateRideDetails(currentPassenger, currentDriver, currentPassenger.promoCode());

                kafkaProducer.sendDriverRequestRide(currentRide);
            } else {
                throw new RuntimeException("No available drivers found. Please try again later!");
            }
        }
    }

    private DriverDto findClosestDriver(PassengerDto passenger, List<DriverDto> drivers) {
        return drivers.stream()
                .filter(driver -> !rejectedDrivers.contains(driver.id()))
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
        return new DriverDto(driver.id(), driver.name(), driver.licenseNumber(), driver.phoneNumber(), driver.location(), status, driver.activityState(), driver.rating());
    }

    private PassengerDto updatePassengerStatus(PassengerDto passenger, String status) {
        return new PassengerDto(passenger.id(), passenger.name(), passenger.email(), passenger.startPoint(), passenger.finalPoint(), status, passenger.rating(), passenger.promoCode());
    }

    @Override
    public void updateRideRating(RideDto rideDto) {
        Ride ride = rideRepository.findById(rideDto.id())
                .orElseThrow(() -> new NoSuchElementException("Ride not found with id: " + rideDto.id()));
        ride.setRating(rideDto.rating());
        rideRepository.save(ride);

        sendDriverRatingToKafka(rideDto.driverId());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendDriverRatingToKafka(String driverId) {
        DriverRating driverRating = driverRatingRepository.findByDriverId(driverId);
        kafkaProducer.sendDriverUpdateRating(driverRatingMapper.toDto(driverRating));
    }

    @Override
    public void updatePassengerRating(RideDto rideDto) {
        Ride ride = rideRepository.findById(rideDto.id())
                .orElseThrow(() -> new NoSuchElementException("Ride not found with id: " + rideDto.id()));
        ride.setPassengerRating(rideDto.passengerRating());
        rideRepository.save(ride);

        sendPassengerRatingToKafka(rideDto.passengerId());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void sendPassengerRatingToKafka(String passengerId) {
        PassengerRating passengerRating = passengerRatingRepository.findByPassengerId(passengerId);
        kafkaProducer.sendPassengerUpdateRating(passengerRatingMapper.toDto(passengerRating));
    }

    @Override
    public void processDriverRequestRide(RideRequestDto rideRequestDto) {
        if ("accepted".equals(rideRequestDto.status())) {
            if (currentRide != null) {
                currentDriver = updateDriverStatus(currentDriver, "accepted");
                currentPassenger = updatePassengerStatus(currentPassenger, "accepted");

                kafkaProducer.sendDriverUpdates(currentDriver);
                kafkaProducer.sendPassengerUpdates(currentPassenger);

                rejectedDrivers.clear();

                kafkaProducer.sendStartRideConfirmation(currentRide);
            }
        } else if ("rejected".equals(rideRequestDto.status())) {
            rejectedDrivers.add(rideRequestDto.driverId());
            currentDriver = updateDriverStatus(currentDriver, "free");
            currentPassenger = updatePassengerStatus(currentPassenger, "waiting");

            kafkaProducer.sendDriverUpdates(currentDriver);
            kafkaProducer.sendPassengerUpdates(currentPassenger);

            kafkaProducer.sendNeedFreeDrivers("Need free drivers for passenger: " + currentPassenger.id());
        }
    }

    @Override
    public void processStartRideConfirmation(RideDto rideDto) {
        if (currentRide != null) {
            currentDriver = updateDriverStatus(currentDriver, "riding");
            currentPassenger = updatePassengerStatus(currentPassenger, "riding");

            kafkaProducer.sendDriverUpdates(currentDriver);
            kafkaProducer.sendPassengerUpdates(currentPassenger);
        }
    }

    @Override
    public void processEndRideConfirmation(RideDto rideDto) {
        if (currentRide != null) {
            currentDriver = updateDriverStatus(currentDriver, "free");
            currentPassenger = updatePassengerStatus(currentPassenger, "completed");

            kafkaProducer.sendDriverUpdates(currentDriver);
            kafkaProducer.sendPassengerUpdates(currentPassenger);

            Ride savedRide = saveRideDetails(rideDto);

            kafkaProducer.sendPaymentRequest(rideMapper.toDto(savedRide));
        }
    }

    private Ride saveRideDetails(RideDto ride) {
        Ride rideEntity = rideMapper.toEntity(ride);
        return rideRepository.save(rideEntity);
    }

    @Override
    public void processPayment(RideDto rideDto) {
        Ride ride = rideRepository.findById(rideDto.id())
                .orElseThrow(() -> new NoSuchElementException("Ride not found with id: " + rideDto.id()));
        ride.setPaid(rideDto.paid());
        rideRepository.save(ride);
    }
}
