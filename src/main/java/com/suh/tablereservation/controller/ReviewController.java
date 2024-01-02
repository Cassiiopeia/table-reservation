package com.suh.tablereservation.controller;

import com.suh.tablereservation.config.JwtAuthenticationProvider;
import com.suh.tablereservation.domain.common.UserDto;
import com.suh.tablereservation.domain.dto.ReviewDto;
import com.suh.tablereservation.domain.form.ReviewCreateForm;
import com.suh.tablereservation.domain.form.ReviewEditForm;
import com.suh.tablereservation.domain.model.Review;
import com.suh.tablereservation.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final JwtAuthenticationProvider provider;

    @PostMapping("/customer/create")
    public ResponseEntity<ReviewDto> createReview(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody ReviewCreateForm form
    ) {
        UserDto userDto = provider.getUserDto(token);
        Review review = reviewService.createReview(userDto.getId(), form);

        return ResponseEntity.ok(ReviewDto.from(review));
    }

    @PutMapping("/customer/edit/{reviewId}")
    public ResponseEntity<ReviewDto> editReview(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @RequestBody ReviewEditForm form,
            @PathVariable Long reviewId
    ) {
        UserDto userDto = provider.getUserDto(token);
        Review review = reviewService.editReview(userDto.getId(), reviewId, form);
        return ResponseEntity.ok(ReviewDto.from(review));
    }

    @DeleteMapping("/delete/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @PathVariable Long reviewId
    ) {
        UserDto userDto = provider.getUserDto(token);
        reviewService.deleteReview(userDto.getId(), reviewId, userDto.getUserType());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customer/search")
    public ResponseEntity<List<ReviewDto>> getCustomerReview(
            @RequestHeader(name = "X-AUTH-TOKEN") String token
    ) {
        UserDto userDto = provider.getUserDto(token);
        List<Review> reviews = reviewService.getCustomerReview(userDto.getId());
        return ResponseEntity.ok(reviews.stream()
                .map(ReviewDto::from)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("store/search/{storeId}")
    public ResponseEntity<List<ReviewDto>> getStoreReview(
            @RequestHeader(name = "X-AUTH-TOKEN") String token,
            @PathVariable Long storeId
    ) {
        UserDto userDto = provider.getUserDto(token);
        List<Review> reviews = reviewService.getStoreReview(userDto.getId(),
                userDto.getUserType(), storeId);
        return ResponseEntity.ok(reviews.stream()
                .map(ReviewDto::from)
                .collect(Collectors.toList()));
    }

}
