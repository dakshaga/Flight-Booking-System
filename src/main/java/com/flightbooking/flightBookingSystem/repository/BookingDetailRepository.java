package com.flightbooking.flightBookingSystem.repository;

import com.flightbooking.flightBookingSystem.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {
    List<BookingDetail> findByBookingId(int bookingId);
}
