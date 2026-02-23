package com.flightbooking.flightBookingSystem.exception.custom;

public class SeatUnavailableException extends RuntimeException{
    public SeatUnavailableException(String message) {
        super(message);
    }
}
