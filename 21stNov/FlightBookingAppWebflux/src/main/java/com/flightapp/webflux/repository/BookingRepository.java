package com.flightapp.webflux.repository;

import com.flightapp.webflux.entity.Booking;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookingRepository extends ReactiveCrudRepository<Booking, Integer> {

    Mono<Booking> findByPnrNumber(String pnrNumber);

    // Booking history by user email (JOIN with users table)
    @Query("""
           SELECT b.* FROM booking b
           JOIN users u ON b.user_id = u.user_id
           WHERE u.email = :email
           ORDER BY b.booking_date DESC
           """)
    Flux<Booking> findByUserEmail(String email);

    // Booking history by user_id (no join needed)
    @Query("""
           SELECT * FROM booking
           WHERE user_id = :userId
           ORDER BY booking_date DESC
           """)
    Flux<Booking> findByUserId(Integer userId);

    Flux<Booking> findByBookingStatus(String status);
}
