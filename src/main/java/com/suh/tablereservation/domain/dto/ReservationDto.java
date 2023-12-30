package com.suh.tablereservation.domain.dto;

import com.suh.tablereservation.domain.common.ReservationStatus;
import com.suh.tablereservation.domain.model.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationDto {
    private LocalDateTime reservationTime;
    private String reservationPhone;
    private String reservationName;
    private Integer numberOfPerson;
    private ReservationStatus status;
    private Long customerId;
    private Long storeId;

    public static ReservationDto from(Reservation reservation){
        return ReservationDto.builder()
                .reservationTime(reservation.getReservationTime())
                .status(reservation.getStatus())
                .reservationPhone(reservation.getReservationPhone())
                .reservationName(reservation.getReservationName())
                .customerId(reservation.getCustomer().getId())
                .storeId(reservation.getStore().getId())
                .build();
    }
}
