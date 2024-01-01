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

import java.util.List;
import java.util.stream.Collectors;

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
    ) {
        UserDto userDto = provider.getUserDto(token);
        Reservation reservation
                = reservationService.createReservation(userDto.getId(), form);

        return ResponseEntity.ok(ReservationDto.from(reservation));
    }

    @GetMapping("/customer/reservations-info")
    public ResponseEntity<List<ReservationDto>> getReservationsByCustomer(
            @RequestHeader(name = "X-AUTH-TOKEN") String token
    ) {
        UserDto userDto = provider.getUserDto(token);
        List<Reservation> reservationDtos
                = reservationService.getReservationsByCustomer(userDto.getId());

        return ResponseEntity.ok(reservationDtos.stream()
                .map(ReservationDto::from)
                .collect(Collectors.toList()));

    }

    @GetMapping("/partner/store-reservation-info/{storeId}")
    public ResponseEntity<List<ReservationDto>> getReservationByStore(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @PathVariable Long storeId
    ) {

        UserDto userDto = provider.getUserDto(token);
        List<Reservation> reservationDtos
                = reservationService.getReservationsByStore(userDto.getId(), storeId);

        return ResponseEntity.ok(reservationDtos.stream()
                .map(ReservationDto::from)
                .collect(Collectors.toList()));
    }

    @PostMapping("partner/confirm/{reservationId}")
    public ResponseEntity<ReservationDto> confirmReservation(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @PathVariable Long reservationId
    ) {
        UserDto userDto = provider.getUserDto(token);
        Reservation reservation = reservationService.confirmReservation(
                userDto.getId(), reservationId);

        return ResponseEntity.ok(ReservationDto.from(reservation));
    }

    @PostMapping("kiosk/visit-confirm/{reservationCode}")
    public ResponseEntity<ReservationDto> confirmVisit(
            @PathVariable String reservationCode
    ) {
        Reservation reservation = reservationService.confirmVisit(
                reservationCode);

        return ResponseEntity.ok(ReservationDto.from(reservation));
    }
}
