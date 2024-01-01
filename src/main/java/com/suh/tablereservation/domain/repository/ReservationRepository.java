package com.suh.tablereservation.domain.repository;

import com.suh.tablereservation.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByCustomerId(Long customerId);

    List<Reservation> findAllByStoreId(Long StoreId);
}
