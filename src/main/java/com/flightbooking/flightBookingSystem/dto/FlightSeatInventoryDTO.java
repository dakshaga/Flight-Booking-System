package com.flightbooking.flightBookingSystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class FlightSeatInventoryDTO {

    private Integer id;
    private Integer flightId;

    @NotBlank(message = "Seat Type is required")
    private String seatType;

    @Positive(message = "Total Seats should be positive")
    private Integer totalSeats;

    @Positive(message = "Available Seats should be positive")
    private Integer availableSeats;

    @Positive(message = "Fare must be positive")
    private Double fare;

    // Empty Constructor
    public FlightSeatInventoryDTO() {

    }

    // Constructor
    public FlightSeatInventoryDTO(Integer id, Integer flightId, String seatType, Integer totalSeats, Integer availableSeats, Double fare) {
        this.id = id;
        this.flightId = flightId;
        this.seatType = seatType;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.fare = fare;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlightId() {
        return flightId;
    }

    public void setFlightId(Integer flightId) {
        this.flightId = flightId;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }
}
