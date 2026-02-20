package com.flightbooking.flightBookingSystem.controller;

import com.flightbooking.flightBookingSystem.dto.BookingDTO;
import com.flightbooking.flightBookingSystem.dto.BookingDetailDTO;
import com.flightbooking.flightBookingSystem.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private final ObjectMapper objectMapper;
    private BookingService bookingService;

    public BookingController(BookingService bookingService, ObjectMapper objectMapper) {
        this.bookingService = bookingService;
        this.objectMapper = objectMapper;
    }

    ////////////////////
    // create booking
    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@RequestBody Map<String, Object> bookingDTO){
        String email = (String) bookingDTO.get("email");

        List<BookingDetailDTO> flightSelections = objectMapper.convertValue(bookingDTO.get("flights"), new TypeReference<List<BookingDetailDTO>>() {});

        BookingDTO booking = bookingService.createBooking(email, flightSelections);

        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    // get booking by emailId
    @GetMapping("/user")
    public ResponseEntity<List<BookingDTO>> getBookingByUserMailId(@RequestParam String emailId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(emailId));
    }

    // get all bookings
    @GetMapping("/all")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }

    // cancel booking
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<String> cancleBooking(@PathVariable int bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok("Booking cancelled Successfully");
    }
}