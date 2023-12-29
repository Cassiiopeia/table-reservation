package com.suh.tablereservation.service;

import com.suh.tablereservation.domain.SignUpForm;
import com.suh.tablereservation.domain.model.Customer;
import com.suh.tablereservation.domain.model.Partner;
import com.suh.tablereservation.domain.repository.CustomerRepository;
import com.suh.tablereservation.domain.repository.PartnerRepository;
import com.suh.tablereservation.exception.CustomException;
import com.suh.tablereservation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SignUpService {
    private final CustomerRepository customerRepository;
    private final PartnerRepository partnerRepository;
    //private final PasswordEncoder passwordEncoder;

    public Customer customerSignUp(SignUpForm form) {
        if (customerRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        Customer newCustomer = Customer.builder()
                .name(form.getName())
                .email(form.getEmail())
                .password(form.getPassword())
                .phone(form.getPhone())
                .birth(form.getBirth())
                .verify(true) // 임시로 true로 설정함
                .build();

        log.info("## SignUpService : customerSignUp : new Customer saved");
        return customerRepository.save(newCustomer);
    }

    public Partner partnerSignUp(SignUpForm form) {
        if (partnerRepository.findByEmail(form.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_EMAIL);
        }

        Partner newPartner = Partner.builder()
                .name(form.getName())
                .email(form.getEmail())
                .password(form.getPassword())
                .phone(form.getPhone())
                .birth(form.getBirth())
                .verify(true) // 임시로 true로 설정함
                .build();

        log.info("## SignUpService : partnerSignUp : new Partner saved");
        return partnerRepository.save(newPartner);

    }
}
