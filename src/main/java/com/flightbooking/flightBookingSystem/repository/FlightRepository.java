package com.flightbooking.flightBookingSystem.repository;

import com.flightbooking.flightBookingSystem.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    // add, update, delete, getById, pagination, sorting, flightByDifferentTime

    Page<Flight> findAll(Pageable pageable);

    @Query("SELECT f FROM Flight f WHERE HOURS(f.departureTime) BETWEEN 4 AND 11")
    List<Flight> findEarlyDepartureFlights();

    @Query("SELECT f FROM Flight f WHERE HOURS(f.departureTime) >= 18  or HOURS(f.departureTime) <= 2")
    List<Flight> findLateDepartureFlights();

    List<Flight> OrderByDurationAsc();

    List<Flight> OrderByDurationDesc();
}
