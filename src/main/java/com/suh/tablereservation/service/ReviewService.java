package com.suh.tablereservation.service;

import com.suh.tablereservation.domain.common.ReservationStatus;
import com.suh.tablereservation.domain.common.UserType;
import com.suh.tablereservation.domain.form.ReviewCreateForm;
import com.suh.tablereservation.domain.form.ReviewEditForm;
import com.suh.tablereservation.domain.model.Reservation;
import com.suh.tablereservation.domain.model.Review;
import com.suh.tablereservation.domain.repository.ReservationRepository;
import com.suh.tablereservation.domain.repository.ReviewRepository;
import com.suh.tablereservation.exception.CustomException;
import com.suh.tablereservation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public Review createReview(Long customerId, ReviewCreateForm form) {

        Reservation reservation = reservationRepository.findById(form.getReservationId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESERVATION));

        if (!reservation.getCustomer().getId().equals(customerId)) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }

        if (!reservation.getStatus().equals(ReservationStatus.VISITED)) {
            throw new CustomException(ErrorCode.NOT_VISITED_RESERVATION_STATUS);
        }

        if (reviewRepository.existsByReservationId(form.getReservationId())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_REVIEW);
        }

        if (LocalDateTime.now().isAfter(reservation.getReservationTime().plusDays(30))
        ) {
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

    @Transactional
    public Review editReview(Long customerId, Long reviewId, ReviewEditForm form) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));

        if (!review.getCustomer().getId().equals(customerId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        review.setContent(form.getContent());
        review.setRating(form.getRating());

        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId, UserType userType) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_REVIEW));

        if (userType == UserType.CUSTOMER && !review.getCustomer().getId().equals(userId) ||
                userType == UserType.PARTNER && !review.getStore().getPartner().getId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_USER);
        }

        reviewRepository.deleteById(reviewId);
        log.info("Review Deleted Successfully:  userType:{}, reviewId= {}",
                userType.toString(), reviewId);
    }

}
