package com.flightbooking.flightBookingSystem.controller;

import com.flightbooking.flightBookingSystem.dto.BookingDTO;
import com.flightbooking.flightBookingSystem.dto.BookingDetailDTO;
import com.flightbooking.flightBookingSystem.payload.ApiResponse;
import com.flightbooking.flightBookingSystem.service.BookingService;
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

    // create booking
    @PostMapping
    public ResponseEntity<ApiResponse<BookingDTO>> createBooking(@RequestBody Map<String, Object> bookingDTO){
        String email = (String) bookingDTO.get("email");
        List<BookingDetailDTO> flightSelections = objectMapper.convertValue(bookingDTO.get("flights"), new TypeReference<List<BookingDetailDTO>>() {});
        BookingDTO booking = bookingService.createBooking(email, flightSelections);

        ApiResponse<BookingDTO> response = new ApiResponse<>(true, "Booking created successfully", booking);
        return ResponseEntity.ok(response);
    }

    // get booking by emailId
    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getBookingByUserMailId(@RequestParam String emailId) {
        List<BookingDTO> bookingDTO = bookingService.getBookingsByUser(emailId);
        ApiResponse<List<BookingDTO>> response = new ApiResponse<>(true, "User booking fetched successfully", bookingDTO);
        return ResponseEntity.ok(response);
    }

    // get all bookings
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<BookingDTO>>> getAllBookings() {
        List<BookingDTO> bookingDTOS = bookingService.getAllBookings();
        ApiResponse<List<BookingDTO>> response = new ApiResponse<>(true, "Fetched all bookings successfully", bookingDTOS);
        return ResponseEntity.ok(response);
    }

    // cancel booking
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<Void>> cancelBooking(@PathVariable int bookingId) {
        bookingService.cancelBooking(bookingId);
        ApiResponse<Void> response = new ApiResponse<>(true, "Booking deleted successfully", null);
        return ResponseEntity.ok(response);
    }
}