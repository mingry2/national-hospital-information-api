package com.mk.national_hospital_information.review.presentation;

import com.mk.national_hospital_information.review.application.interfaces.ReviewLikeService;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewLikeRestController {

    private final ReviewLikeService reviewLikeService;
    private final UserService userService;

    @PostMapping("/{reviewId}/review-like")
    public ResponseEntity<String> toggleReviewLike(@PathVariable Long reviewId) {
        Long loginId = getUserId();

        String result = reviewLikeService.toggleReviewLike(loginId, reviewId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(result);
    }

    private Long getUserId() {
        return userService.findByUsername(
            SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

}
