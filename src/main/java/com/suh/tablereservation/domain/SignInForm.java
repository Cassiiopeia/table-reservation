package com.suh.tablereservation.domain;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInForm {
    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDateTime birth;
}
