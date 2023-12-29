package com.suh.tablereservation.service;

import com.suh.tablereservation.domain.repository.CustomerRepository;
import com.suh.tablereservation.domain.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInService {
    private final CustomerRepository customerRepository;
    private final PartnerRepository partnerRepository;


}
