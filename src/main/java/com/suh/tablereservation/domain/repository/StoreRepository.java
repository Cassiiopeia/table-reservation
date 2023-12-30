package com.suh.tablereservation.domain.repository;

import com.suh.tablereservation.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String name);

    List<Store> findByPartnerId(Long partnerId);

    List<Store> findByNameContainingIgnoreCase(String storeName);

    Optional<Store> findByIdAndPartnerId(Long storeId, Long partnerId);
}
