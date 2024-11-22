package com.modsen.driverservice.repository;

import com.modsen.driverservice.model.Driver;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DriverRepository extends MongoRepository<Driver, String> {
    List<Driver> findByRideStatusAndActivityState(String rideStatus, String activityState);
}