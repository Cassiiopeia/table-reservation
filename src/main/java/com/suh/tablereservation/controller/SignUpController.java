package com.suh.tablereservation.controller;

import com.suh.tablereservation.domain.form.SignUpForm;
import com.suh.tablereservation.domain.dto.CustomerDto;
import com.suh.tablereservation.domain.dto.PartnerDto;
import com.suh.tablereservation.domain.model.Customer;
import com.suh.tablereservation.domain.model.Partner;
import com.suh.tablereservation.service.SignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signup")
public class SignUpController {
    public final SignUpService signUpService;


    @PostMapping("/customer")
    public ResponseEntity<CustomerDto> customerSignUp(
            @RequestBody SignUpForm signUpForm){
        Customer customer = signUpService.customerSignUp(signUpForm);
        return ResponseEntity.ok(CustomerDto.from(customer));
    }

    @PostMapping("/partner")
    public ResponseEntity<PartnerDto> partnerSignUp(
            @RequestBody SignUpForm signUpForm){
        Partner partner = signUpService.partnerSignUp(signUpForm);
        return ResponseEntity.ok(PartnerDto.from(partner));

    }


}
