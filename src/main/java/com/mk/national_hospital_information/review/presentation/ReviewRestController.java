package com.mk.national_hospital_information.review.presentation;

import com.mk.national_hospital_information.common.exception.Response;
import com.mk.national_hospital_information.review.application.ReviewServiceImpl;
import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.presentation.dto.ReviewAddRequestDto;
import com.mk.national_hospital_information.review.presentation.dto.ReviewAddResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hospital")
@RequiredArgsConstructor
public class ReviewRestController {

    private final ReviewServiceImpl reviewServiceImpl;

    @PostMapping("/{hospitalId}/review")
    public ResponseEntity<Response<ReviewAddResponseDto>> addReview(@PathVariable Long hospitalId, @RequestBody ReviewAddRequestDto reviewAddRequestDto) {
        Review review = reviewServiceImpl.add(hospitalId, reviewAddRequestDto);
        ReviewAddResponseDto reviewAddResponseDto = new ReviewAddResponseDto(review.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(reviewAddResponseDto));
    }

}
