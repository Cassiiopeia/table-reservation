package com.suh.tablereservation.domain.form;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationCreateForm {
    private String storeName;
    private String reservationName;
    private String reservationPhone;
    private LocalDateTime reservationTime;
    private Integer numberOfPerson;

}
