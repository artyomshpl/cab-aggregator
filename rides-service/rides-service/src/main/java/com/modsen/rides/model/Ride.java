package com.modsen.rides.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "rides")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String passengerId;
    private String driverId;
    private Long waitTime;
    private Long travelTime;
    private Double routeLength;
    private BigDecimal price;
    private Integer rating;
}
