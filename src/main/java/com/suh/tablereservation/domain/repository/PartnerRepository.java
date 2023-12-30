package com.suh.tablereservation.domain.repository;

import com.suh.tablereservation.domain.model.Partner;
import com.suh.tablereservation.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Optional<Partner> findByEmail(String email);
    Optional<Partner> findById(Long id);
}
