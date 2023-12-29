package com.suh.tablereservation.domain.dto;

import com.suh.tablereservation.domain.model.Partner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PartnerDto {
    private Long id;

    private String name;

    private String email;

    public static PartnerDto from(Partner partner){
        return PartnerDto.builder()
                .id(partner.getId())
                .name(partner.getName())
                .email(partner.getEmail())
                .build();
    }

}
