package com.flightapp.webflux.repository;

import com.flightapp.webflux.entity.Airline;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AirlineRepository extends ReactiveCrudRepository<Airline, Integer> {

    Mono<Airline> findByAirlineCode(String airlineCode);

    Mono<Airline> findByAirlineName(String airlineName);
}
