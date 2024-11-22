package com.modsen.driverservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverDTO {
    private String id;
    private String name;
    private String licenseNumber;
    private String phoneNumber;
    private String location;
    private String rideStatus;
    private String activityState;
}