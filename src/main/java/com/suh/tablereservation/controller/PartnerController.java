package com.suh.tablereservation.controller;

import com.suh.tablereservation.config.JwtAuthenticationProvider;
import com.suh.tablereservation.domain.common.UserDto;
import com.suh.tablereservation.domain.dto.PartnerDto;
import com.suh.tablereservation.domain.model.Partner;
import com.suh.tablereservation.exception.CustomException;
import com.suh.tablereservation.exception.ErrorCode;
import com.suh.tablereservation.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/partner")
@RequiredArgsConstructor
public class PartnerController {
    private final JwtAuthenticationProvider provider;
    private final PartnerService partnerService;

    @GetMapping("/getinfo")
    public ResponseEntity<PartnerDto> getInfo(
            @RequestHeader(name = "X-AUTH-TOKEN") String token
    ){
        UserDto userDto = provider.getUserDto(token);
        Partner partner = partnerService.findByIdAndEmail(userDto.getId(), userDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        return ResponseEntity.ok(PartnerDto.from(partner));
    }

}
