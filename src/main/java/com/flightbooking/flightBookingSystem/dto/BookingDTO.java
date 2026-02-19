package com.flightbooking.flightBookingSystem.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDTO {
    private Integer id;
    private String userName;
    private String userEmail;
    private LocalDateTime bookingTime;
    private Double totalAmount;
    private List<BookingDetailDTO> bookingDetails;

    // Empty Constructor
    public BookingDTO() {

    }

    // Constructor
    public BookingDTO(Integer id, String userName, String userEmail, LocalDateTime bookingTime, Double totalAmount, List<BookingDetailDTO> bookingDetails) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.bookingTime = bookingTime;
        this.totalAmount = totalAmount;
        this.bookingDetails = bookingDetails;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<BookingDetailDTO> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(List<BookingDetailDTO> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
}
