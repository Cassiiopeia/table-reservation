package com.suh.tablereservation.domain.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder
public class StoreCreateForm {

    private String name;
    private String location;
    private String description;

    private LocalTime openingTime;
    private LocalTime closingTime;
    private Boolean isAvaliableReservation;
    private Integer maxReservationPeople;

}
