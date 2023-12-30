package com.suh.tablereservation.controller;

import com.suh.tablereservation.config.JwtAuthenticationProvider;
import com.suh.tablereservation.service.CustomerService;
import com.suh.tablereservation.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final JwtAuthenticationProvider provider;
    private final CustomerService customerService;

}
