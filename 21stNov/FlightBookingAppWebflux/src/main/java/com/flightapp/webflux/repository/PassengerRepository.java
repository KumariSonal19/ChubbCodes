package com.flightapp.webflux.repository;

import com.flightapp.webflux.entity.Passenger;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface PassengerRepository extends ReactiveCrudRepository<Passenger, Integer> {

    Flux<Passenger> findByBookingId(Integer bookingId);
}
