package com.modsen.rides.util;

public interface DistanceAndDurationParser {
    Long parseDurationToSeconds(String duration);
    Double parseDistanceToKilometers(String distance);
}
