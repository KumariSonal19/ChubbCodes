package com.flightapp.webflux.repository;

import com.flightapp.webflux.entity.Flight;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface FlightRepository extends ReactiveCrudRepository<Flight, Integer> {

    Mono<Flight> findByFlightNumber(String flightNumber);

    @Query("""
           SELECT * FROM flight f
           WHERE f.departure_city = :departure
             AND f.arrival_city = :arrival
             AND DATE(f.departure_time) = :date
             AND f.available_seats > 0
             AND f.is_active = TRUE
           """)
    Flux<Flight> searchFlights(String departure, String arrival, LocalDate date);

    Flux<Flight> findByStatus(String status);
}
