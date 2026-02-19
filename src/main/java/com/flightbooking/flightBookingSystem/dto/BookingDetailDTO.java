package com.flightbooking.flightBookingSystem.dto;

public class BookingDetailDTO {
    private Integer id;
    private Integer flightId;
    private String flightSource;
    private String flightDestination;
    private String seatType;
    private Double fare;

    // Empty Constructor
    public BookingDetailDTO() {

    }

    // Constructor
    public BookingDetailDTO(Integer id, Integer flightId, String flightSource, String flightDestination, String seatType, Double fare) {
        this.id = id;
        this.flightId = flightId;
        this.flightSource = flightSource;
        this.flightDestination = flightDestination;
        this.seatType = seatType;
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

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }
}