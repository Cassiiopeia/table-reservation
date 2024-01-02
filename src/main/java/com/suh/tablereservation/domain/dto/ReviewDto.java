package com.suh.tablereservation.domain.dto;

import com.suh.tablereservation.domain.model.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReviewDto {
    private Integer rating;
    private String content;
    private LocalDateTime reviewDate;

    private Long customerId;
    private Long storeId;
    private Long reservationId;

    public static ReviewDto from(Review review){
        return ReviewDto.builder()
                .rating(review.getRating())
                .content(review.getContent())
                .reviewDate(review.getReviewDate())
                .customerId(review.getCustomer().getId())
                .storeId(review.getStore().getId())
                .reservationId(review.getReservation().getId())
                .build();
    }
}
