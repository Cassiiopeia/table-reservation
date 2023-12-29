package com.suh.tablereservation.domain.dto;
import com.suh.tablereservation.domain.model.Customer;
import com.suh.tablereservation.domain.model.Partner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomerDto {
    private Long id;

    private String name;

    private String email;

    public static CustomerDto from(Customer customer){
        return CustomerDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
    }
}
