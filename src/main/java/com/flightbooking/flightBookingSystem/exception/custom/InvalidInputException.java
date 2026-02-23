package com.flightbooking.flightBookingSystem.exception.custom;

public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String message) {
        super(message);
    }
}
