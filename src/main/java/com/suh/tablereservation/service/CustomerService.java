package com.suh.tablereservation.service;

import com.suh.tablereservation.domain.model.Customer;
import com.suh.tablereservation.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Optional<Customer> findByIdAndEmail(Long id, String email){
        return customerRepository.findById(id).stream().filter(
                customer -> customer.getEmail().equals(email)).findFirst();
    }
}
