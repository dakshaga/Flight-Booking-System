package com.flightbooking.flightBookingSystem.repository;

import com.flightbooking.flightBookingSystem.entity.FlightSeatInventory;
import com.flightbooking.flightBookingSystem.enums.SeatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlightSeatInventoryRepository extends JpaRepository<FlightSeatInventory, Integer> {

    Optional<FlightSeatInventory> findByFlightIdAndSeatType(int flightId, SeatType seatType);
}
