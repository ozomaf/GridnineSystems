package com.gridnine.testing;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class FlightFilterOverstayedTimeOnGround implements FlightFilter {
    private static final int MAX_HOURS_ON_GROUND = 2;

    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream().filter(flight -> calculateTotalTimeOnGround(flight) <= MAX_HOURS_ON_GROUND)
                .collect(Collectors.toList());
    }

    private int calculateTotalTimeOnGround(Flight flight) {
        int totalTimeOnGround = 0;
        List<Segment> segments = flight.getSegments();
        for (int i = 0; i < segments.size() - 1; i++) {
            Duration timeBetween = Duration.between(
                    segments.get(i).getArrivalDate(),
                    segments.get(i + 1).getDepartureDate());
            totalTimeOnGround += timeBetween.toHours();
        }
        return totalTimeOnGround;
    }

}