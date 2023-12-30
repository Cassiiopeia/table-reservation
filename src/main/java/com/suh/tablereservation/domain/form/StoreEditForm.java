package com.suh.tablereservation.domain.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StoreEditForm {
    private String name;
    private String location;
    private String description;
}
