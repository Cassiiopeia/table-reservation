package com.suh.tablereservation.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpForm {
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDateTime birth;
}
