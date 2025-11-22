package com.flightapp.webflux.service;

import com.flightapp.webflux.dto.BookingRequestDTO;
import com.flightapp.webflux.dto.BookingResponseDTO;
import com.flightapp.webflux.dto.PassengerDTO;
import com.flightapp.webflux.entity.*;
import com.flightapp.webflux.repository.*;
import com.flightapp.webflux.exception.ResourceNotFoundException;
import com.flightapp.webflux.exception.BookingException;
import com.flightapp.webflux.validation.ValidationUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final PassengerRepository passengerRepository;

    public Mono<BookingResponseDTO> bookFlight(Integer flightId, BookingRequestDTO request) {

        if (flightId == null || flightId <= 0)
            return Mono.error(new BookingException("Invalid flight ID"));

        return userRepository.findByEmail(request.getUserEmail())
                .switchIfEmpty(createGuestUser(request.getUserEmail()))
                .flatMap(user -> flightRepository.findById(flightId)
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException("Flight not found")))

                        .flatMap(flight -> validateBooking(flight, request)
                                .then(createBookingRecord(user, flight, request))

                                .flatMap(booking -> passengerRepository
                                        .saveAll(createPassengerEntities(booking, request.getPassengers()))
                                        .collectList()
                                        .thenReturn(booking))

                                .flatMap(booking -> updateSeatsAfterBooking(flight, request)
                                        .thenReturn(booking))

                                .flatMap(this::convertToDTOReactive)
                        )
                );
    }

    public Mono<BookingResponseDTO> cancelBooking(String pnr) {
        return bookingRepository.findByPnrNumber(pnr)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Booking not found")))
                .flatMap(booking -> flightRepository.findById(booking.getFlightId())
                        .flatMap(flight -> {

	                    	 if ("CANCELLED".equals(booking.getBookingStatus())) {
	                             return Mono.error(new ResourceNotFoundException("Booking already cancelled for PNR: " + pnr));
	                         }
                        	 
                            if (LocalDateTime.now().isAfter(flight.getDepartureTime().minusHours(24)))
                                return Mono.error(new BookingException("Cannot cancel within 24 hours of departure"));

                            booking.setBookingStatus("CANCELLED");
                            booking.setCancellationDate(LocalDateTime.now());
                            booking.setRefundAmount(booking.getTotalPrice());

                            flight.setAvailableSeats(flight.getAvailableSeats()+ booking.getNumberOfPassengers());

                            return bookingRepository.save(booking)
                                    .then(flightRepository.save(flight))
                                    .then(convertToDTOReactive(booking));
                        }));
    }

    public Mono<BookingResponseDTO> getBookingByPNR(String pnr) {
        return bookingRepository.findByPnrNumber(pnr)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("Booking not found")))
                .flatMap(this::convertToDTOReactive);
    }

    public Flux<BookingResponseDTO> getBookingHistory(String email) {
        ValidationUtils.validateEmail(email);

        return bookingRepository.findByUserEmail(email)
                .flatMap(this::convertToDTOReactive);
    }

    
    private Mono<User> createGuestUser(String email) {
        User user = User.builder()
                .email(email)
                .firstName("Guest")
                .lastName("User")
                .password("guest_" + System.currentTimeMillis())
                .isActive(true)
                .build();

        return userRepository.save(user);
    }

    private Mono<Void> validateBooking(Flight flight, BookingRequestDTO request) {
        return Mono.fromRunnable(() -> {

            ValidationUtils.validateNumberOfPassengers(request.getNumberOfPassengers());
            ValidationUtils.validateAvailableSeats(flight.getAvailableSeats(), request.getNumberOfPassengers());

            if (LocalDateTime.now().isAfter(flight.getDepartureTime()))
                throw new BookingException("Cannot book past departure time");

            if (request.getPassengers().size() != request.getNumberOfPassengers())
                throw new BookingException("Passenger count mismatch");
        });
    }

    private Mono<Booking> createBookingRecord(User user, Flight flight, BookingRequestDTO request) {
        Booking booking = Booking.builder()
                .pnrNumber(generatePNR())
                .userId(user.getUserId())
                .flightId(flight.getFlightId())
                .numberOfPassengers(request.getNumberOfPassengers())
                .bookingStatus("CONFIRMED")
                .tripType(request.getTripType())
                .totalPrice(flight.getPricePerSeat()
                        .multiply(BigDecimal.valueOf(request.getNumberOfPassengers())))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .bookingDate(LocalDateTime.now())
                .build();

        return bookingRepository.save(booking);
    }

    private Flux<Passenger> createPassengerEntities(Booking booking, 
                                                    java.util.List<PassengerDTO> list) {

        return Flux.fromIterable(list)
                .map(dto -> Passenger.builder()
                        .bookingId(booking.getBookingId())
                        .passengerName(dto.getPassengerName())
                        .gender(dto.getGender())
                        .age(dto.getAge())
                        .mealPreference(dto.getMealPreference())
                        .baggageAllowanceKg(20)
                        .isActive(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
    }

    private Mono<Void> updateSeatsAfterBooking(Flight flight, BookingRequestDTO request) {
        flight.setAvailableSeats(flight.getAvailableSeats() - request.getNumberOfPassengers());
        flight.setUpdatedAt(LocalDateTime.now());
        return flightRepository.save(flight).then();
    }

    private String generatePNR() {
        return "PNR" + String.format("%07d", System.currentTimeMillis() % 10000000);
    }

    private Mono<BookingResponseDTO> convertToDTOReactive(Booking booking) {
        return flightRepository.findById(booking.getFlightId())
                .map(flight -> BookingResponseDTO.builder()
                        .bookingId(booking.getBookingId())
                        .pnrNumber(booking.getPnrNumber())
                        .flightNumber(flight.getFlightNumber())
                        .departureCity(flight.getDepartureCity())
                        .arrivalCity(flight.getArrivalCity())
                        .departureTime(flight.getDepartureTime())
                        .totalPrice(booking.getTotalPrice())
                        .bookingStatus(booking.getBookingStatus())
                        .bookingDate(booking.getBookingDate())
                        .numberOfPassengers(booking.getNumberOfPassengers())
                        .build());
    }
}
