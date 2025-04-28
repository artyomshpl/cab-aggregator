package com.modsen.rides.service.impl;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;

import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.modsen.rides.dto.DistanceAndDurationDto;
import com.modsen.rides.exception.DirectionsException;
import com.modsen.rides.service.DirectionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class DirectionsServiceImpl implements DirectionService {

    @Value("${google.maps.api.key:0000-000-000-0000}")
    private String apiKey;

    @Override
    @Transactional
    public DistanceAndDurationDto getDirections(String origin, String destination) {
        DirectionsResult result;

        try {
            result = fetchDirections(origin, destination);
        } catch (Exception e) {
            throw new DirectionsException("Error fetching directions: " + origin + " Destination: " + destination + " message: " + e.getMessage());
        }

        if(result.routes.length == 0){
            throw new DirectionsException("No routes found. Origin: " + origin + " Destination: " + destination);
        } else {
            DirectionsLeg leg = result.routes[0].legs[0];
            return new DistanceAndDurationDto(leg.distance.humanReadable, leg.duration.humanReadable);
        }
    }

    private DirectionsResult fetchDirections(String origin, String destination) throws Exception {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();

        return DirectionsApi.newRequest(context)
                .mode(TravelMode.DRIVING)
                .origin(origin)
                .destination(destination)
                .await();
    }
}