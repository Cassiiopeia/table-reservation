package com.suh.tablereservation.controller;

import com.suh.tablereservation.config.JwtAuthenticationProvider;
import com.suh.tablereservation.domain.common.UserDto;
import com.suh.tablereservation.domain.dto.ReviewDto;
import com.suh.tablereservation.domain.form.ReviewCreateForm;
import com.suh.tablereservation.domain.model.Review;
import com.suh.tablereservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final JwtAuthenticationProvider provider;

    @PostMapping("/create")
    public ResponseEntity<ReviewDto> createReview(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody ReviewCreateForm form
            ){
        UserDto userDto = provider.getUserDto(token);
        Review review = reviewService.createReview(userDto.getId(), form);

        return ResponseEntity.ok(ReviewDto.from(review));

    }


}
