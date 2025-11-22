package com.flightapp.webflux.controller;

import com.flightapp.webflux.dto.FlightResponseDTO;
import com.flightapp.webflux.dto.FlightSearchDTO;
import com.flightapp.webflux.entity.Flight;
import com.flightapp.webflux.exception.ValidationException;
import com.flightapp.webflux.service.FlightService;

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
@RequestMapping("/flight")
@RequiredArgsConstructor
@Slf4j
public class FlightController {

    private final FlightService flightService;

    
    @PostMapping("/airline/inventory/add")
    public Mono<ResponseEntity<Map<String, Object>>> addFlightInventory(
            @RequestBody Mono<Flight> flightMono) {

        return flightMono.flatMap(flight -> {
            log.info("Adding new flight: {}", flight.getFlightNumber());

            return flightService.addFlight(flight)
                    .map(addedFlight -> {
                        Map<String, Object> response = new HashMap<>();
                        response.put("message", "Flight inventory added successfully");
                        response.put("flightId", addedFlight.getFlightId());
                        response.put("flightNumber", addedFlight.getFlightNumber());
                        response.put("airlineName", addedFlight.getAirlineName());
                        response.put("departureCity", addedFlight.getDepartureCity());
                        response.put("arrivalCity", addedFlight.getArrivalCity());
                        response.put("availableSeats", addedFlight.getAvailableSeats());
                        response.put("pricePerSeat", addedFlight.getPricePerSeat());

                        return ResponseEntity.status(HttpStatus.CREATED).body(response);
                    });
        });
    }

   
    @PostMapping("/search")
    public Flux<FlightResponseDTO> searchFlights(@RequestBody FlightSearchDTO searchDTO) {

        log.info("Searching flights from {} to {}", searchDTO.getDepartureCity(), searchDTO.getArrivalCity());

        if (searchDTO.getDepartureCity() == null || searchDTO.getDepartureCity().trim().isEmpty()) {
            throw new ValidationException("Departure city is required");
        }
        if (searchDTO.getArrivalCity() == null || searchDTO.getArrivalCity().trim().isEmpty()) {
            throw new ValidationException("Arrival city is required");
        }
        if (searchDTO.getDepartureDate() == null) {
            throw new ValidationException("Departure date is required");
        }
        if (searchDTO.getNumberOfPassengers() == null || searchDTO.getNumberOfPassengers() <= 0) {
            throw new ValidationException("Number of passengers must be greater than 0");
        }

        return flightService.searchFlights(searchDTO);
    }

  
    @GetMapping("/{flightId}")
    public Mono<ResponseEntity<FlightResponseDTO>> getFlightDetails(@PathVariable Integer flightId) {

        log.info("Fetching flight details for ID: {}", flightId);

        if (flightId == null || flightId <= 0) {
            throw new ValidationException("Invalid flight ID");
        }

        return flightService.getFlightById(flightId)
                .map(ResponseEntity::ok);
    }
}
