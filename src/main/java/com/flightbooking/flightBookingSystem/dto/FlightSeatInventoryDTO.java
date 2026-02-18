package com.flightbooking.flightBookingSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class FlightSeatInventoryDTO {

    private int id;
    private int flightId;

    @NotBlank(message = "Seat Type is required")
    private String seatType;

    @Positive(message = "Total Seats should be positive")
    private int totalSeats;

    @Positive(message = "Available Seats should be positive")
    private int availableSeats;

    @Positive(message = "Fare must be positive")
    private double fare;

    // Empty Constructor
    public FlightSeatInventoryDTO() {

    }

    // Constructor
    public FlightSeatInventoryDTO(int id, int flightId, String seatType, int totalSeats, int availableSeats, double fare) {
        this.id = id;
        this.flightId = flightId;
        this.seatType = seatType;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.fare = fare;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }
}
