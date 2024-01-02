package com.suh.tablereservation.service;

import com.suh.tablereservation.domain.common.ReservationStatus;
import com.suh.tablereservation.domain.common.UserType;
import com.suh.tablereservation.domain.form.ReviewCreateForm;
import com.suh.tablereservation.domain.form.ReviewEditForm;
import com.suh.tablereservation.domain.model.Customer;
import com.suh.tablereservation.domain.model.Reservation;
import com.suh.tablereservation.domain.model.Review;
import com.suh.tablereservation.domain.model.Store;
import com.suh.tablereservation.domain.repository.*;
import com.suh.tablereservation.exception.CustomException;
import com.suh.tablereservation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Review createReview(Long customerId, ReviewCreateForm form) {

        Reservation reservation = reservationRepository.findById(form.getReservationId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RESERVATION));

        validateReviewForm(form.getRating(), form.getContent());

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
            throw new CustomException(ErrorCode.INVALID_USER);
        }

        validateReviewForm(form.getRating(), form.getContent());

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
            throw new CustomException(ErrorCode.INVALID_USER);
        }

        reviewRepository.deleteById(reviewId);
        log.info("Review Deleted Successfully:  userType:{}, reviewId= {}",
                userType.toString(), reviewId);
    }

    @Transactional(readOnly = true)
    public List<Review> getCustomerReview(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return reviewRepository.findAllByCustomer(customer);
    }

    @Transactional(readOnly = true)
    public List<Review> getStoreReview(Long partnerId, UserType userType, Long storeId) {
        if (!userType.equals(UserType.PARTNER)) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_STORE));
        if (!store.getPartner().getId().equals(partnerId)) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }
        return reviewRepository.findAllByStore(store);
    }

    private void validateReviewForm(int rating, String content) {
        if (rating < 0 || rating > 5) {
            throw new CustomException(ErrorCode.INVALID_REVIEW_RATING);
        }
        if (content.length() > 500) {
            throw new CustomException(ErrorCode.INVALID_REVIEW_CONTENT_LENGTH);
        }
    }


}
