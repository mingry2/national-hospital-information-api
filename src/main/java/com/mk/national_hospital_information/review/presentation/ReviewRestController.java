package com.mk.national_hospital_information.review.presentation;

import com.mk.national_hospital_information.common.exception.Response;
import com.mk.national_hospital_information.review.application.interfaces.ReviewService;
import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.presentation.dto.ReviewFindResponseDto;
import com.mk.national_hospital_information.review.presentation.dto.ReviewRequestDto;
import com.mk.national_hospital_information.review.presentation.dto.ReviewResponseDto;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "📑 3. Review Controller", description = "리뷰 등록, 수정, 삭제, 조회(단건), 조회(전체)")
public class ReviewRestController {

    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping("/{hospitalId}/review")
    @Operation(summary = "✔ 리뷰 등록", description = "📢 title(제목), content(내용), satisfaction(만족도)으로 리뷰를 등록합니다.")
    public ResponseEntity<Response<ReviewResponseDto>> addReview(@PathVariable Long hospitalId, @RequestBody ReviewRequestDto reviewAddRequestDto) {
        Long loginId = getUserId();

        Review savedReview = reviewService.save(hospitalId, loginId, reviewAddRequestDto);
        ReviewResponseDto reviewAddResponseDto = new ReviewResponseDto(savedReview.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(reviewAddResponseDto));
    }

    @PutMapping("/{hospitalId}/review/{reviewId}")
    @Operation(summary = "✔ 리뷰 수정", description = "📢 title(제목), content(내용), satisfaction(만족도)으로 리뷰를 수정합니다.")
    public ResponseEntity<Response<ReviewResponseDto>> updateReview(@PathVariable Long hospitalId, @PathVariable Long reviewId, @RequestBody ReviewRequestDto reviewUpdateRequestDto) {
        Long loginId = getUserId();

        Review updatedReview = reviewService.update(hospitalId, reviewId, loginId, reviewUpdateRequestDto);

        ReviewResponseDto reviewUpdateResponseDto = new ReviewResponseDto(updatedReview.getId());

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(Response.success(reviewUpdateResponseDto));
    }

    @PatchMapping("/{hospitalId}/review/{reviewId}")
    @Operation(summary = "✔ 리뷰 삭제", description = "📢 리뷰를 삭제합니다.(단, 데이터는 완전 삭제되지 않으며, deleted_at을 통해 관리됩니다.(soft delete)")
    public ResponseEntity<String> deleteReview(@PathVariable Long hospitalId, @PathVariable Long reviewId) {
        Long loginId = getUserId();

        String result = reviewService.delete(hospitalId, reviewId, loginId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(result);
    }

    @GetMapping("/{hospitalId}/review/{reviewId}")
    @Operation(summary = "✔ 리뷰 조회(단건)", description = "📢 단건 리뷰를 조회합니다.(병원 Id, 리뷰 Id, 제목, 내용, 만족도, 회원 Id 표시)")
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
    @Operation(summary = "✔ 리뷰 조회(전체)", description = "📢 전체 리뷰를 조회합니다.(병원 Id, 리뷰 Id, 제목, 내용, 만족도, 회원 Id 표시 - 1페이지 당 20건)")
    public Page<Review> findAllReview(Pageable pageable) {

        return reviewService.findAll(pageable);
    }

    private Long getUserId() {
        return userService.findByUsername(
            SecurityContextHolder.getContext().getAuthentication().getName()).getId();
    }

}
