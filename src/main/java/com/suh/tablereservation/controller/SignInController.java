package com.suh.tablereservation.controller;

import com.suh.tablereservation.domain.SignInForm;
import com.suh.tablereservation.service.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signin")
public class SignInController {
    public final SignInService signInService;


    @RequestMapping("/customer")
    public ResponseEntity<String> customerSignIn(SignInForm signInForm){
        return ResponseEntity.ok("");
    }

    @RequestMapping("/partner")
    public ResponseEntity<String> partnerSignIn(SignInForm signInForm){

        return ResponseEntity.ok("");
    }

}
