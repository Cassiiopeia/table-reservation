package com.suh.tablereservation.domain.dto;

import com.suh.tablereservation.domain.model.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class StoreDto {

    private Long id;
    private String name;
    private String location;
    private String description;

    public static StoreDto from(Store store){
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .description(store.getDescription())
                .build();
    }
}
