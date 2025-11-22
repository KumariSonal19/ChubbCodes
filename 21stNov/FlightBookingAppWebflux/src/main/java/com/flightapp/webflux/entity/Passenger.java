package com.flightapp.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("passenger")
public class Passenger {

    @Id
    @Column("passenger_id")
    private Integer passengerId;

    // FK instead of Booking entity
    @Column("booking_id")
    private Integer bookingId;

    @Column("passenger_name")
    private String passengerName;

    @Column("gender")
    private String gender;

    @Column("age")
    private Integer age;

    @Column("date_of_birth")
    private LocalDate dateOfBirth;

    @Column("email")
    private String email;

    @Column("phone_number")
    private String phoneNumber;

    @Column("meal_preference")
    private String mealPreference;

    @Column("seat_number")
    private String seatNumber;

    @Column("baggage_allowance_kg")
    private Integer baggageAllowanceKg;

    @Column("is_active")
    private Boolean isActive;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
