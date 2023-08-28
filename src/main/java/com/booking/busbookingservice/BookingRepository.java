package com.booking.busbookingservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, String> {
   Booking findByBookingid(String bookingid);
}