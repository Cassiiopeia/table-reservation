package com.suh.tablereservation.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String location;
    private String description;

    private LocalTime openingTime;
    private LocalTime closingTime;
    private Boolean isAvaliableReservation;
    private Integer maxReservationPeople;

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

}
