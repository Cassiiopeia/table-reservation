package com.suh.tablereservation.controller;

import com.suh.tablereservation.config.JwtAuthenticationProvider;
import com.suh.tablereservation.domain.common.UserDto;
import com.suh.tablereservation.domain.dto.ReservationDto;
import com.suh.tablereservation.domain.form.ReservationForm;
import com.suh.tablereservation.domain.model.Reservation;
import com.suh.tablereservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final JwtAuthenticationProvider provider;

    @PostMapping("/customer/create")
    public ResponseEntity<ReservationDto> createResrvation(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody ReservationForm form
            ){
        UserDto userDto = provider.getUserDto(token);
        Reservation reservation
                = reservationService.createReservation(userDto.getId(), form);

        return ResponseEntity.ok(ReservationDto.from(reservation));
    }
}
