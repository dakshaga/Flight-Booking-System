package com.flightbooking.flightBookingSystem.dto;

public class BookingDetailDTO {
    private int id;
    private int flightId;
    private String flightSource;
    private String flightDestination;
    private String seatType;
    private double fare;

    // Empty Constructor
    public BookingDetailDTO() {

    }

    // Constructor
    public BookingDetailDTO(int id, int flightId, String flightSource, String flightDestination, String seatType, double fare) {
        this.id = id;
        this.flightId = flightId;
        this.flightSource = flightSource;
        this.flightDestination = flightDestination;
        this.seatType = seatType;
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

    public String getFlightSource() {
        return flightSource;
    }

    public void setFlightSource(String flightSource) {
        this.flightSource = flightSource;
    }

    public String getFlightDestination() {
        return flightDestination;
    }

    public void setFlightDestination(String flightDestination) {
        this.flightDestination = flightDestination;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }
}
