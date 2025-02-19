package com.mk.national_hospital_information.review.presentation;

import com.mk.national_hospital_information.review.application.interfaces.ReviewLikeService;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "📑 4. ReviewLike Controller", description = "리뷰 좋아요")
public class ReviewLikeRestController {

    private final ReviewLikeService reviewLikeService;
    private final UserService userService;

    @PostMapping("/{reviewId}/review-like")
    @Operation(summary = "✔ 리뷰 좋아요", description = "📢 리뷰를 '좋아요'합니다.(한번 클릭, 좋아요 / 두번 클릭, 좋아요 취소)")
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
