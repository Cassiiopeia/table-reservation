package com.suh.tablereservation.domain.form;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SignUpForm {
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDate birth;
}
