package com.suh.tablereservation.domain.form;

import lombok.Getter;

@Getter
public class ReviewCreateForm {
    private Long reservationId;
    private Integer rating;
    private String content;
}
