package com.suh.tablereservation.domain.repository;

import com.suh.tablereservation.domain.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Boolean existsByReservationId(Long reservationId);
}
