package com.suh.tablereservation.service;

import com.suh.tablereservation.config.JwtAuthenticationProvider;
import com.suh.tablereservation.domain.SignInForm;
import com.suh.tablereservation.domain.common.UserType;
import com.suh.tablereservation.domain.model.Customer;
import com.suh.tablereservation.domain.model.Partner;
import com.suh.tablereservation.domain.repository.CustomerRepository;
import com.suh.tablereservation.domain.repository.PartnerRepository;
import com.suh.tablereservation.exception.CustomException;
import com.suh.tablereservation.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInService {
    private final CustomerRepository customerRepository;
    private final PartnerRepository partnerRepository;
    private final JwtAuthenticationProvider provider;

    public String customerSignInToken(SignInForm form){
        Customer signInCustomer = customerRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        if(!form.getPassword().equals(signInCustomer.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        return provider.createToken(signInCustomer.getEmail(),
                signInCustomer.getId(),
                UserType.CUSTOMER);
    }

    public String partnerSignInToken(SignInForm form){
        Partner signInPartner = partnerRepository.findByEmail(form.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        if(!form.getPassword().equals(signInPartner.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        return provider.createToken(signInPartner.getEmail(),
                signInPartner.getId(),
                UserType.PARTNER);
    }

}
