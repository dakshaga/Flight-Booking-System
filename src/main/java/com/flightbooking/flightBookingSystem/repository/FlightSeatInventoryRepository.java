package com.flightbooking.flightBookingSystem.repository;

import com.flightbooking.flightBookingSystem.entity.FlightSeatInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightSeatInventoryRepository extends JpaRepository<FlightSeatInventory, Integer> {

    List<FlightSeatInventory> findByFlightId(Integer flightId);
}
