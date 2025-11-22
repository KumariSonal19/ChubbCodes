package com.flightapp.webflux.entity;

import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor      
@Builder
@Table("airline")
public class Airline {

    @Id
    @Column("airline_id")
    private Integer airlineId;

    @Column("airline_name")
    private String airlineName;

    @Column("airline_code")
    private String airlineCode;

    @Column("logo_url")
    private String logoUrl;

    @Column("contact_number")
    private String contactNumber;

    @Column("email")
    private String email;

    @Column("is_active")
    private Boolean isActive;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
