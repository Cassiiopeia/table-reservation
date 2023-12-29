package com.suh.tablereservation.service;

import com.suh.tablereservation.domain.model.Partner;
import com.suh.tablereservation.domain.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerService {
    private final PartnerRepository partnerRepository;

    public Optional<Partner> findByIdAndEmail(Long id, String email){
        return partnerRepository.findById(id).stream().filter(
                customer -> customer.getEmail().equals(email)).findFirst();
    }

}
