package com.flightbooking.flightBookingSystem.exception.custom;

public class DuplicateResourceException extends RuntimeException{
    public DuplicateResourceException(String message) {
        super(message);
    }
}
