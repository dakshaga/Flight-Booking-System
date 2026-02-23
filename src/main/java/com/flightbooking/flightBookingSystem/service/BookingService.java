package com.flightbooking.flightBookingSystem.service;

import com.flightbooking.flightBookingSystem.dto.*;
import com.flightbooking.flightBookingSystem.entity.*;
import com.flightbooking.flightBookingSystem.enums.SeatType;
import com.flightbooking.flightBookingSystem.exception.custom.InvalidInputException;
import com.flightbooking.flightBookingSystem.exception.custom.ResourceNotFoundException;
import com.flightbooking.flightBookingSystem.exception.custom.SeatUnavailableException;
import com.flightbooking.flightBookingSystem.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingDetailRepository bookingDetailRepository;
    private final FlightRepository flightRepository;
    private final FlightSeatInventoryRepository flightSeatInventoryRepository;
    private final UserRepository userRepository;

    // Constructor
    public BookingService(BookingRepository bookingRepository, BookingDetailRepository bookingDetailRepository, FlightRepository flightRepository, FlightSeatInventoryRepository flightSeatInventoryRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.bookingDetailRepository = bookingDetailRepository;
        this.flightRepository = flightRepository;
        this.flightSeatInventoryRepository = flightSeatInventoryRepository;
        this.userRepository = userRepository;
    }

    // Create Booking
    public BookingDTO createBooking(String emailId, List<BookingDetailDTO> flightSelections) {
        User user = userRepository.findByEmail(emailId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id:  " + emailId));

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBookingTime(LocalDateTime.now());
        booking.setAmount(0);
        booking = bookingRepository.save(booking);

        double totalFare = 0;
        List<BookingDetail> bookingDetails = new ArrayList<>();
        for(BookingDetailDTO detailDTO : flightSelections) {
            Flight flight = flightRepository.findById(detailDTO.getFlightId()).orElseThrow(
                    ()-> new ResourceNotFoundException("Flight not found: " + detailDTO.getFlightId()));

            // Here we have checked whether that seat type is in our enum
            SeatType seatType;
            try {
                seatType = SeatType.valueOf(detailDTO.getSeatType().toUpperCase());
            } catch (Exception e) {
                throw new InvalidInputException("Invalid seat type: " + detailDTO.getSeatType());
            }

            // now will check where flightId and seatType combination occurs???
            FlightSeatInventory inventory = flightSeatInventoryRepository
                    .findByFlightIdAndSeatType(flight.getId(), seatType)
                    .orElseThrow(() -> new ResourceNotFoundException("Seat Inventory Not Found for flight id: " + flight.getId() + " and seat tpye: " + seatType));

            if(inventory.getAvailableSeats() <= 0) {
                throw new SeatUnavailableException("Seat are not available in the flight with id: " + flight.getId());
            }

            inventory.setAvailableSeats(inventory.getAvailableSeats() -1);
            flightSeatInventoryRepository.save(inventory);

            BookingDetail bookingDetail = new BookingDetail();
            bookingDetail.setBooking(booking);
            bookingDetail.setFlight(flight);
            bookingDetail.setSeatType(seatType);
            bookingDetail.setFare(inventory.getFare());
            bookingDetails.add(bookingDetail);

            totalFare += inventory.getFare();
        }

        booking.setBookingDetails(bookingDetails);
        booking.setAmount(totalFare);
        bookingRepository.save(booking);
        bookingDetailRepository.saveAll(bookingDetails);

        return mapToDTO(booking);
    }

    // get bookings by user emailId
    public List<BookingDTO> getBookingsByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with mailId: " + email));

        List<Booking> bookings = bookingRepository.findByUserId(user.getId());
        List<BookingDTO> bookingDTO = new ArrayList<>();
        for(Booking booking : bookings) {
            bookingDTO.add(mapToDTO(booking));
        }

        return bookingDTO;
    }

    // Get all bookings
    public List<BookingDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        List<BookingDTO> bookingListDTOs = new ArrayList<>();
        for(Booking booking : bookings) {
            bookingListDTOs.add(mapToDTO(booking));
        }

        return  bookingListDTOs;
    }

    // Cancel Bookings
    public void cancelBooking(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        for(BookingDetail details : booking.getBookingDetails()) {
            FlightSeatInventory inventory = flightSeatInventoryRepository.findByFlightIdAndSeatType(details.getFlight().getId(), details.getSeatType())
                    .orElseThrow(() -> new ResourceNotFoundException("Seat Inventory not found: " + details.getFlight().getId()));

            inventory.setAvailableSeats(inventory.getAvailableSeats() + 1);
            flightSeatInventoryRepository.save(inventory);
        }

        bookingRepository.delete(booking);
    }

    // entity -> DTO
    private BookingDTO mapToDTO(Booking booking) {
        List<BookingDetailDTO> bookingDetailListDTOS = new ArrayList<>();
        for(BookingDetail detail : booking.getBookingDetails()) {
            BookingDetailDTO bookingDetailDTO = new BookingDetailDTO(
                    detail.getId(),
                    detail.getFlight().getId(),
                    detail.getFlight().getSource(),
                    detail.getFlight().getDestination(),
                    detail.getSeatType().name(),
                    detail.getFare()
            );
            bookingDetailListDTOS.add(bookingDetailDTO);
        }
        return new BookingDTO(
                booking.getId(),
                booking.getUser().getName(),
                booking.getUser().getEmail(),
                booking.getBookingTime(),
                booking.getAmount(),
                bookingDetailListDTOS
        );
    }
}

