package com.flightbooking.flightBookingSystem.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookingDTO {
    private int id;
    private String userName;
    private String userEmail;
    private LocalDateTime bookingTime;
    private double totalAmount;
    private List<BookingDetailDTO> bookingDetails;

    // Empty Constructor
    public BookingDTO() {

    }

    // Constructor
    public BookingDTO(int id, String userName, String userEmail, LocalDateTime bookingTime, double totalAmount, List<BookingDetailDTO> bookingDetails) {
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
        this.bookingTime = bookingTime;
        this.totalAmount = totalAmount;
        this.bookingDetails = bookingDetails;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<BookingDetailDTO> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(List<BookingDetailDTO> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }
}
