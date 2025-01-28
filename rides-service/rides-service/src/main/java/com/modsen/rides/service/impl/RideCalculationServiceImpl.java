package com.modsen.rides.service.impl;

import com.modsen.rides.client.RedisClient;
import com.modsen.rides.dto.DistanceAndDurationDto;
import com.modsen.rides.dto.DriverDto;
import com.modsen.rides.dto.PassengerDto;
import com.modsen.rides.dto.RideDto;
import com.modsen.rides.service.DirectionService;
import com.modsen.rides.service.RideCalculationService;
import com.modsen.rides.util.DistanceAndDurationParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideCalculationServiceImpl implements RideCalculationService {
    private final DirectionService directionService;
    private final DistanceAndDurationParser distanceAndDurationParser;
    private final RedisClient redisClient;

    @Value("${price.per.kilometer:1.0}")
    private BigDecimal pricePerKilometer;

    public RideDto calculateRideDetails(PassengerDto passenger, DriverDto driver, String promoCode) {
        DistanceAndDurationDto toPassenger = directionService.getDirections(driver.location(), passenger.startPoint());
        DistanceAndDurationDto toDestination = directionService.getDirections(passenger.startPoint(), passenger.finalPoint());

        Long waitTime = distanceAndDurationParser.parseDurationToSeconds(toPassenger.duration());
        Long travelTime = distanceAndDurationParser.parseDurationToSeconds(toDestination.duration());
        Double routeLength = distanceAndDurationParser.parseDistanceToKilometers(toDestination.distance());
        BigDecimal price = calculatePrice(routeLength, promoCode);

        return new RideDto(
                null,
                passenger.id().toString(),
                driver.id(),
                waitTime,
                travelTime,
                routeLength,
                price,
                null,
                null,
                false
        );
    }

    private BigDecimal calculatePrice(Double routeLength, String promoCode) {
        BigDecimal basePrice = BigDecimal.valueOf(routeLength).multiply(pricePerKilometer);

        if (promoCode != null) {
            String discountValue = redisClient.getValue(promoCode);
            if (discountValue != null) {
                try {
                    BigDecimal discount = new BigDecimal(discountValue);
                    return basePrice.multiply(BigDecimal.ONE.subtract(discount.divide(BigDecimal.valueOf(100))));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("Invalid discount value format for promo code: " + promoCode);
                }
            }
        }

        return basePrice;
    }
}
