package com.modsen.rides.util.impl;

import com.modsen.rides.util.DistanceAndDurationParser;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DistanceAndDurationUtils implements DistanceAndDurationParser {

    @Override
    public Long parseDurationToSeconds(String duration) {
        Pattern pattern = Pattern.compile("(\\d+)\\s*(\\w+)");
        Matcher matcher = pattern.matcher(duration);
        long totalSeconds = 0;

        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);
            switch (unit.toLowerCase()) {
                case "hour":
                case "hours":
                    totalSeconds += value * 3600;
                    break;
                case "min":
                case "mins":
                    totalSeconds += value * 60;
                    break;
                case "sec":
                case "secs":
                    totalSeconds += value;
                    break;
            }
        }
        return totalSeconds;
    }

    @Override
    public Double parseDistanceToKilometers(String distance) {
        Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?)\\s*(\\w+)");
        Matcher matcher = pattern.matcher(distance);
        double totalKilometers = 0;

        if (matcher.find()) {
            double value = Double.parseDouble(matcher.group(1));
            String unit = matcher.group(3);
            switch (unit.toLowerCase()) {
                case "km":
                    totalKilometers += value;
                    break;
                case "m":
                    totalKilometers += value / 1000.0;
                    break;
            }
        }
        return totalKilometers;
    }
}
