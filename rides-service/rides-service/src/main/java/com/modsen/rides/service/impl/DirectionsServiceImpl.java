package com.modsen.rides.service.impl;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.modsen.rides.dto.DistanceAndDurationDto;
import com.modsen.rides.exception.DirectionsException;
import com.modsen.rides.service.DirectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DirectionsServiceImpl implements DirectionService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    @Override
    public DistanceAndDurationDto getDirections(String origin, String destination) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        try {
            DirectionsResult result = DirectionsApi.newRequest(context)
                    .mode(TravelMode.DRIVING)
                    .origin(origin)
                    .destination(destination)
                    .await();

            if (result.routes.length > 0) {
                com.google.maps.model.DirectionsRoute route = result.routes[0];
                com.google.maps.model.DirectionsLeg leg = route.legs[0];
                return new DistanceAndDurationDto(leg.distance.humanReadable, leg.duration.humanReadable);
            } else {
                throw new DirectionsException("No routes found");
            }
        } catch (Exception e) {
            throw new DirectionsException("Error fetching directions: " + e.getMessage());
        }
    }
}
