package com.suh.tablereservation.domain.repository;

import com.suh.tablereservation.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findById(Long reservationId);
    Optional<Reservation> findByReservationCode(String reservationCode);
    List<Reservation> findAllByCustomerId(Long customerId);

    List<Reservation> findAllByStoreId(Long StoreId);
}
