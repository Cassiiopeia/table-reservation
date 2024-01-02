package com.suh.tablereservation.domain.repository;

import com.suh.tablereservation.domain.model.Customer;
import com.suh.tablereservation.domain.model.Review;
import com.suh.tablereservation.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Boolean existsByReservationId(Long reservationId);

    List<Review> findAllByCustomer(Customer customer);
    List<Review> findAllByStore(Store store);
}
