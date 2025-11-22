package com.flightapp.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("booking")
public class Booking {

    @Id
    @Column("booking_id")
    private Integer bookingId;

    @Column("pnr_number")
    private String pnrNumber;

    // store foreign keys instead of User / Flight entities
    @Column("user_id")
    private Integer userId;

    @Column("flight_id")
    private Integer flightId;

    @Column("number_of_passengers")
    private Integer numberOfPassengers;

    @Column("total_price")
    private BigDecimal totalPrice;

    @Column("booking_status")
    private String bookingStatus;  // Use enum class

    @Column("booking_date")
    private LocalDateTime bookingDate;

    @Column("journey_date")
    private LocalDate journeyDate;

    @Column("return_date")
    private LocalDate returnDate;

    @Column("trip_type")
    private String tripType;       // ONE_WAY/ROUND_TRIP

    @Column("cancellation_date")
    private LocalDateTime cancellationDate;

    @Column("cancellation_reason")
    private String cancellationReason;

    @Column("refund_amount")
    private BigDecimal refundAmount;

    @Column("is_active")
    private Boolean isActive;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
