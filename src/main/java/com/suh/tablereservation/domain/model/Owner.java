package com.suh.tablereservation.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Owner extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String phone;
    private LocalDateTime birth;

    private LocalDateTime verifyExpiredAt;
    private String verificationCode;
    private boolean verify;

    @OneToMany
    @JoinColumn(name = "store_id")
    private List<Store> stores;

}
