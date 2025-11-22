package com.flightapp.webflux.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("seat")
public class Seat {

    @Id
    @Column("seat_id")
    private Integer seatId;

    @Column("flight_id")
    private Integer flightId;      

    @Column("seat_number")
    private String seatNumber;

    @Column("seat_class")
    private String seatClass;

    @Column("is_available")
    private Boolean isAvailable;

    @Column("is_reserved")
    private Boolean isReserved;

    @Column("booking_id")
    private Integer bookingId;      

    @Column("reserved_at")
    private LocalDateTime reservedAt;

    @Column("is_active")
    private Boolean isActive;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
