package com.flightapp.webflux.controller;

import com.flightapp.webflux.dto.BookingRequestDTO;
import com.flightapp.webflux.dto.BookingResponseDTO;
import com.flightapp.webflux.exception.ValidationException;
import com.flightapp.webflux.service.BookingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/flight/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;
    
    @PostMapping("/{flightId}")
    public Mono<ResponseEntity<BookingResponseDTO>> bookFlight(@PathVariable Integer flightId,
            @RequestBody Mono<BookingRequestDTO> requestMono) {

        log.info("Processing booking for flight {}", flightId);

        if (flightId == null || flightId <= 0) {
            return Mono.error(new ValidationException("Invalid flight ID"));
        }

        return requestMono
        		.switchIfEmpty(Mono.error(new ValidationException("Booking request cannot be null")))
        		.flatMap(request -> {
                    if (request.getUserEmail() == null || request.getUserEmail().trim().isEmpty()) {
                        return Mono.error(new ValidationException("User email is required"));
                    }
                    if (request.getNumberOfPassengers() == null || request.getNumberOfPassengers() <= 0) {
                        return Mono.error(new ValidationException("Number of passengers must be greater than 0"));
                    }
                    if (request.getPassengers() == null || request.getPassengers().isEmpty()) {
                        return Mono.error(new ValidationException("Passenger details are required"));
                    }
                    if (request.getPassengers().size() != request.getNumberOfPassengers()) {
                        return Mono.error(new ValidationException("Passenger count mismatch"));
                    }

                    return bookingService.bookFlight(flightId, request);
                })
                .doOnNext(booking ->log.info("Booking created with PNR: {}", booking.getPnrNumber()))
                .map(booking ->ResponseEntity.status(HttpStatus.CREATED).body(booking));
    }

    @GetMapping("/ticket/{pnr}")
    public Mono<ResponseEntity<BookingResponseDTO>> getTicket(@PathVariable String pnr) {

        log.info("Fetching booking for PNR: {}", pnr);

        if (pnr == null || pnr.trim().isEmpty()) {
            return Mono.error(new ValidationException("PNR cannot be empty"));
        }

        return bookingService.getBookingByPNR(pnr)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/history/{emailId}")
    public Flux<BookingResponseDTO> getBookingHistory(@PathVariable String emailId) {

        log.info("Fetching booking history for email: {}", emailId);

        if (emailId == null || emailId.trim().isEmpty()) {
            throw new ValidationException("Email cannot be empty");
        }

        return bookingService.getBookingHistory(emailId)
                .doOnSubscribe(s -> log.info("Loading booking history..."))
                .doOnComplete(() -> log.info("Finished loading booking history for {}", emailId));
    }

    @DeleteMapping("/cancel/{pnr}")
    public Mono<ResponseEntity<Map<String, Object>>> cancelBooking(@PathVariable String pnr) {

        log.info("Cancelling booking with PNR: {}", pnr);

        if (pnr == null || pnr.trim().isEmpty()) {
            return Mono.error(new ValidationException("PNR cannot be empty"));
        }

        return bookingService.cancelBooking(pnr).map(cancelled -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Booking cancelled successfully");
                    response.put("pnrNumber", cancelled.getPnrNumber());
                    response.put("refundAmount", cancelled.getTotalPrice());
                    log.info("Booking cancelled: {}", pnr);
                    return ResponseEntity.ok(response);
                });
    }
}
