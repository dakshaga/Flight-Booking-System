package com.flightbooking.flightBookingSystem.exception;

import com.flightbooking.flightBookingSystem.exception.custom.DuplicateResourceException;
import com.flightbooking.flightBookingSystem.exception.custom.InvalidInputException;
import com.flightbooking.flightBookingSystem.exception.custom.ResourceNotFoundException;
import com.flightbooking.flightBookingSystem.exception.custom.SeatUnavailableException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException exception, HttpServletRequest request) {
        return buildResponse(exception, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<ErrorResponse> handleInvalidInput(InvalidInputException exception, HttpServletRequest request) {
        return buildResponse(exception, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(SeatUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleSeatUnavailable(SeatUnavailableException exception, HttpServletRequest request) {
        return buildResponse(exception, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateResourceException exception, HttpServletRequest request) {
        return buildResponse(exception, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception, HttpServletRequest request) {
        return buildResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<ErrorResponse> buildResponse(Exception exception, HttpStatus status, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error,status);
    }
}
