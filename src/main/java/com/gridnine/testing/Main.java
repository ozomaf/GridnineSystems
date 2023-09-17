package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println(new FlightFilterDepartureBeforeNow().filter(flights));
        System.out.println(new FlightFilterArrivalBeforeDeparture().filter(flights));
        System.out.println(new FlightFilterOverstayedTimeOnGround().filter(flights));
    }

}