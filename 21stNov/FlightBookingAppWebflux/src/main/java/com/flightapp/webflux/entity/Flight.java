package com.flightapp.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("flight")
public class Flight {

    @Id
    @Column("flight_id")
    private Integer flightId;

    // store airline FK instead of Airline entity
    @Column("airline_id")
    private Integer airlineId;

    @Column("flight_number")
    private String flightNumber;

    @Column("departure_city")
    private String departureCity;

    @Column("arrival_city")
    private String arrivalCity;

    @Column("departure_time")
    private LocalDateTime departureTime;

    @Column("arrival_time")
    private LocalDateTime arrivalTime;

    @Column("total_seats")
    private Integer totalSeats;

    @Column("available_seats")
    private Integer availableSeats;

    @Column("price_per_seat")
    private BigDecimal pricePerSeat;

    @Column("aircraft_type")
    private String aircraftType;

    @Column("status")
    private String status;      

    @Column("is_active")
    private Boolean isActive;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
