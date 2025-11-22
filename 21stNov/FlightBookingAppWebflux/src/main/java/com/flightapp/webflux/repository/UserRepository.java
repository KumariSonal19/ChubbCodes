package com.flightapp.webflux.repository;

import com.flightapp.webflux.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    Mono<User> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);
}
