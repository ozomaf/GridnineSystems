package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlightFilterTest {

    private final List<Flight> flights = FlightBuilder.createFlights();

    @Test
    void testFilterDepartureBeforeNow() {
        LocalDateTime now = LocalDateTime.now();
        FlightFilterDepartureBeforeNow filter = new FlightFilterDepartureBeforeNow();

        List<Flight> filtered = filter.filter(flights);

        for (Flight flight : filtered) {
            assertTrue(flight.getSegments()
                    .stream()
                    .allMatch(segment -> segment.getDepartureDate().isAfter(now)));
        }
    }

    @Test
    void testFilterArrivalBeforeDeparture() {
        FlightFilterArrivalBeforeDeparture filter = new FlightFilterArrivalBeforeDeparture();

        List<Flight> filtered = filter.filter(flights);

        for (Flight flight : filtered) {
            assertTrue(flight.getSegments()
                    .stream()
                    .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())));
        }
    }

    @Test
    public void testFilterOverstayedTimeOnGround() {
        LocalDateTime now = LocalDateTime.now();
        FlightFilterOverstayedTimeOnGround filter = new FlightFilterOverstayedTimeOnGround();

        List<Segment> invalidSegments = List.of(
                new Segment(now, now.plusHours(1)),
                new Segment(now.plusHours(4), now.plusHours(5)));

        List<Flight> flights = List.of(new Flight(invalidSegments));
        List<Flight> filteredFlights = filter.filter(flights);

        assertEquals(filteredFlights.size(), 0);
    }

}