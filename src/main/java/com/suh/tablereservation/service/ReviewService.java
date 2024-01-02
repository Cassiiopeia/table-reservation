package com.suh.tablereservation.service;

import com.suh.tablereservation.domain.common.ReservationStatus;
import com.suh.tablereservation.domain.form.ReviewCreateForm;
import com.suh.tablereservation.domain.model.Reservation;
import com.suh.tablereservation.domain.model.Review;
import com.suh.tablereservation.domain.repository.CustomerRepository;
import com.suh.tablereservation.domain.repository.ReservationRepository;
import com.suh.tablereservation.domain.repository.ReviewRepository;
import com.suh.tablereservation.domain.repository.StoreRepository;
import com.suh.tablereservation.exception.CustomException;
import com.suh.tablereservation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@ Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Review createReview(Long customerId, ReviewCreateForm form){

        Reservation reservation = reservationRepository.findById(form.getReservationId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESERVATION));

        if(!reservation.getCustomer().getId().equals(customerId)) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }

        if(!reservation.getStatus().equals(ReservationStatus.VISITED)){
            throw new CustomException(ErrorCode.NOT_VISITED_RESERVATION_STATUS);
        }

        if(reviewRepository.existsByReservationId(form.getReservationId())){
            throw new CustomException(ErrorCode.ALREADY_EXIST_REVIEW);
        }

        if(LocalDateTime.now().isAfter( reservation.getReservationTime().plusDays(30))
        ){
            throw new CustomException(ErrorCode.EXPIRED_REVIEW_CREATE_TIME);
        }

        Review review = Review.builder()
                .rating(form.getRating())
                .content(form.getContent())
                .reviewDate(LocalDateTime.now())
                .customer(reservation.getCustomer())
                .store(reservation.getStore())
                .reservation(reservation)
                .build();

        return reviewRepository.save(review);
    }
}
