package com.suh.tablereservation.controller;

import com.suh.tablereservation.domain.form.SignInForm;
import com.suh.tablereservation.service.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signin")
public class SignInController {
    public final SignInService signInService;


    @PostMapping("/customer")
    public ResponseEntity<String> customerSignInToken(SignInForm signInForm){
        String token = signInService.customerSignInToken(signInForm);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/partner")
    public ResponseEntity<String> partnerSignInToken(SignInForm signInForm){
        String token = signInService.partnerSignInToken(signInForm);
        return ResponseEntity.ok(token);
    }

}
