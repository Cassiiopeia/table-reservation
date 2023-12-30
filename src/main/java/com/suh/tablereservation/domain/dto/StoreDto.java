package com.suh.tablereservation.domain.dto;

import com.suh.tablereservation.domain.model.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;


@Getter
@Setter
@Builder
public class StoreDto {

    private Long id;
    private String name;
    private String location;
    private String description;

    private LocalTime openingTime;
    private LocalTime closingTime;
    private Boolean isAvaliableReservation;
    private Integer maxReservationPeople;

    private Long partnerId;

    public static StoreDto from(Store store){
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .description(store.getDescription())
                .openingTime(store.getOpeningTime())
                .closingTime(store.getClosingTime())
                .isAvaliableReservation(store.getIsAvaliableReservation())
                .maxReservationPeople(store.getMaxReservationPeople())
                .partnerId(store.getPartner().getId())
                .build();
    }
}
