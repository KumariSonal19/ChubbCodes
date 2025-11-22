package com.flightapp.webflux.repository;

import com.flightapp.webflux.entity.Seat;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SeatRepository extends ReactiveCrudRepository<Seat, Integer> {

    // All available seats for a flight
    Flux<Seat> findByFlightIdAndIsAvailableTrue(Integer flightId);

    // Specific seat for a flight
    Mono<Seat> findByFlightIdAndSeatNumber(Integer flightId, String seatNumber);
}
