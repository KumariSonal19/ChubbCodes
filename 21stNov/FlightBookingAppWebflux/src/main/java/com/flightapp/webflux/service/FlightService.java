package com.flightapp.webflux.service;

import com.flightapp.webflux.dto.FlightResponseDTO;
import com.flightapp.webflux.dto.FlightSearchDTO;
import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.exception.ResourceNotFoundException;
import com.flightapp.webflux.exception.ValidationException;
import com.flightapp.webflux.repository.AirlineRepository;
import com.flightapp.webflux.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;

    public Flux<FlightResponseDTO> searchFlights(FlightSearchDTO searchDTO) {
        log.info("Searching flights from {} to {} on {}",
                searchDTO.getDepartureCity(),
                searchDTO.getArrivalCity(),
                searchDTO.getDepartureDate());

        if (searchDTO.getDepartureCity() == null || searchDTO.getDepartureCity().isBlank() ||
            searchDTO.getArrivalCity() == null || searchDTO.getArrivalCity().isBlank()) {
            throw new ValidationException("Departure and arrival cities are required");
        }
        if (searchDTO.getDepartureDate() == null) {
            throw new ValidationException("Departure date is required");
        }

        return flightRepository.searchFlights(
                        searchDTO.getDepartureCity(),
                        searchDTO.getArrivalCity(),
                        searchDTO.getDepartureDate()
                ).flatMap(this::toDtoWithAirline);
    }

    @Transactional
    public Mono<FlightResponseDTO> addFlight(Flight flight) {
        return validateFlight(flight)
                .then(flightRepository.findByFlightNumber(flight.getFlightNumber())
                        .flatMap(existing ->
                                Mono.<Flight>error(new ValidationException(
                                        "Flight with this number already exists")))
                        .switchIfEmpty(Mono.defer(() -> {
                            
                            flight.setAvailableSeats(flight.getTotalSeats());
                            if (flight.getStatus() == null) {
                                flight.setStatus("ACTIVE");
                            }
                            if (flight.getIsActive() == null) {
                                flight.setIsActive(true);
                            }
                            LocalDateTime now = LocalDateTime.now();
                            if (flight.getCreatedAt() == null) {
                                flight.setCreatedAt(now);
                            }
                            flight.setUpdatedAt(now);
                            return flightRepository.save(flight);
                        })))
                .flatMap(this::toDtoWithAirline);
    }

    public Mono<FlightResponseDTO> getFlightById(Integer flightId) {
        log.info("Fetching flight with ID: {}", flightId);

        if (flightId == null || flightId <= 0) {
            return Mono.error(new ValidationException("Invalid flight ID"));
        }

        return flightRepository.findById(flightId)
                .switchIfEmpty(Mono.error(
                        new ResourceNotFoundException("Flight not found with ID: " + flightId)))
                .flatMap(this::toDtoWithAirline);
    }

    @Transactional
    public Mono<Void> addFlightInventory(Flight flight) {
        log.info("Adding flight inventory for flight: {}", flight.getFlightNumber());

        return validateFlight(flight)
                .then(flightRepository.findByFlightNumber(flight.getFlightNumber())
                        .flatMap(existing ->
                                Mono.<Flight>error(new IllegalArgumentException(
                                        "Flight with this number already exists")))
                        .switchIfEmpty(Mono.defer(() -> {
                            flight.setAvailableSeats(flight.getTotalSeats());
                            if (flight.getIsActive() == null) {
                                flight.setIsActive(true);
                            }
                            LocalDateTime now = LocalDateTime.now();
                            if (flight.getCreatedAt() == null) {
                                flight.setCreatedAt(now);
                            }
                            flight.setUpdatedAt(now);
                            return flightRepository.save(flight);
                        })))
                .then();
    }


    private Mono<Void> validateFlight(Flight flight) {
        return Mono.fromRunnable(() -> {
            if (flight.getFlightNumber() == null || flight.getFlightNumber().isBlank()) {
                throw new ValidationException("Flight number is required");
            }
            if (flight.getAirlineId() == null) {   
                throw new ValidationException("Airline ID is required");
            }
            if (flight.getDepartureCity() == null || flight.getArrivalCity() == null) {
                throw new ValidationException("Departure and arrival cities are required");
            }
            if (flight.getTotalSeats() == null || flight.getTotalSeats() <= 0) {
                throw new ValidationException("Total seats must be greater than 0");
            }
            if (flight.getPricePerSeat() == null
                    || flight.getPricePerSeat().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ValidationException("Price per seat must be greater than 0");
            }
            if (flight.getDepartureTime() == null || flight.getArrivalTime() == null) {
                throw new ValidationException("Departure and arrival time are required");
            }
        });
    }

    private Mono<FlightResponseDTO> toDtoWithAirline(Flight flight) {
        return airlineRepository.findById(flight.getAirlineId())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(
                        "Airline not found with ID: " + flight.getAirlineId())))
                .map(airline -> FlightResponseDTO.builder()
                        .flightId(flight.getFlightId())
                        .flightNumber(flight.getFlightNumber())
                        .airlineName(airline.getAirlineName())
                        .aircraftType(flight.getAircraftType())
                        .departureCity(flight.getDepartureCity())
                        .arrivalCity(flight.getArrivalCity())
                        .departureTime(flight.getDepartureTime())
                        .arrivalTime(flight.getArrivalTime())
                        .availableSeats(flight.getAvailableSeats())
                        .pricePerSeat(flight.getPricePerSeat())
                        .build());
    }
}
