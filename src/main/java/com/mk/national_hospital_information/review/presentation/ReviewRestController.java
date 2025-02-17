package com.mk.national_hospital_information.review.presentation;

import com.mk.national_hospital_information.common.exception.Response;
import com.mk.national_hospital_information.review.application.interfaces.ReviewService;
import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.presentation.dto.ReviewFindResponseDto;
import com.mk.national_hospital_information.review.presentation.dto.ReviewRequestDto;
import com.mk.national_hospital_information.review.presentation.dto.ReviewResponseDto;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hospital")
@RequiredArgsConstructor
public class ReviewRestController {

    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping("/{hospitalId}/review")
    public ResponseEntity<Response<ReviewResponseDto>> addReview(@PathVariable Long hospitalId, @RequestBody ReviewRequestDto reviewAddRequestDto) {
        Long loginId = getUserId();

        Review savedReview = reviewService.save(hospitalId, loginId, reviewAddRequestDto);
        ReviewResponseDto reviewAddResponseDto = new ReviewResponseDto(savedReview.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(reviewAddResponseDto));
    }

    @PutMapping("/{hospitalId}/review/{reviewId}")
    public ResponseEntity<Response<ReviewResponseDto>> updateReview(@PathVariable Long hospitalId, @PathVariable Long reviewId, @RequestBody ReviewRequestDto reviewUpdateRequestDto) {
        Long loginId = getUserId();

        Review updatedReview = reviewService.update(hospitalId, reviewId, loginId, reviewUpdateRequestDto);

        ReviewResponseDto reviewUpdateResponseDto = new ReviewResponseDto(updatedReview.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(reviewUpdateResponseDto));
    }

    @PatchMapping("/{hospitalId}/review/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long hospitalId, @PathVariable Long reviewId) {
        Long loginId = getUserId();

        String result = reviewService.delete(hospitalId, reviewId, loginId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(result);
    }

    @GetMapping("/{hospitalId}/review/{reviewId}")
    public ResponseEntity<Response<ReviewFindResponseDto>> findReview(@PathVariable Long hospitalId, @PathVariable Long reviewId) {
        Review findReview = reviewService.findByReviewId(hospitalId, reviewId);

        ReviewFindResponseDto reviewFindResponseDto = new ReviewFindResponseDto(
            hospitalId,
            reviewId,
            findReview.getTitle(),
            findReview.getContent(),
            findReview.getSatisfaction(),
            findReview.getUserId()
        );

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(reviewFindResponseDto));
    }

    @GetMapping("/reviews")
    public Page<Review> findAllReview(Pageable pageable) {

        return reviewService.findAll(pageable);
    }

    private Long getUserId() {
        return userService.findByUsername(
            SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

}
