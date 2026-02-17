package com.flightbooking.flightBookingSystem.repository;

import com.flightbooking.flightBookingSystem.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {

    @Query("SELECT f FROM Flight f WHERE FUNCTION('HOUR', f.departureTime) BETWEEN 4 AND 11")
    List<Flight> findEarlyDepartureFlights();

    @Query("SELECT f FROM Flight f WHERE FUNCTION('HOUR', f.departureTime) >= 18 OR FUNCTION('HOUR', f.departureTime) <= 2")
    List<Flight> findLateDepartureFlights();
}
