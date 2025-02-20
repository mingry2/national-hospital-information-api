package com.mk.national_hospital_information.review.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.hospital.application.interfaces.HospitalRepository;
import com.mk.national_hospital_information.review.application.interfaces.ReviewRepository;
import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.presentation.dto.ReviewRequestDto;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import com.mk.national_hospital_information.user.domain.User;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private HospitalRepository hospitalRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    private Review review;
    private ReviewRequestDto dto;
    private User user;

    private final Long hospitalId = 1L;
    private final Long reviewId = 1L;
    private final Long loginId = 1L;


    @BeforeEach
    void init() {
        dto = new ReviewRequestDto("testTitle", "testContent", 5L);
        review = new Review(1L, "testTitle", "testContent", 1L, 1L, 1L);
        user = new User(1L, "testUser");
    }

    @Test
    @DisplayName("리뷰 등록 성공")
    void save() {
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(userService.verifyUserId(anyLong())).thenReturn(user);

        Review result = reviewService.save(1L, 1L, dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getTitle()).isEqualTo(review.getTitle());
        assertThat(result.getContent()).isEqualTo(review.getContent());
        assertThat(result.getSatisfaction()).isEqualTo(review.getSatisfaction());
    }

    @Test
    @DisplayName("리뷰 등록 실패 - 존재하지 않는 병원")
    void save_fail_HOSPITAL_NOT_FOUND() {
        when(hospitalRepository.findById(anyLong())).thenThrow(new GlobalException(
            ErrorCode.HOSPITAL_NOT_FOUND,
            ErrorCode.HOSPITAL_NOT_FOUND.getMessage()
        ));
        when(userService.verifyUserId(anyLong())).thenReturn(user);

        assertThatThrownBy(() -> reviewService.save(1L, 1L, dto))
            .isInstanceOf(GlobalException.class)
            .hasMessageContaining(ErrorCode.HOSPITAL_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("리뷰 수정 성공")
    void update() {
        Review updateReview = new Review(1L, "updateTitle", "updateContent", 5L, 1L, 1L);
        when(reviewRepository.update(anyLong(), anyLong(), any(ReviewRequestDto.class))).thenReturn(updateReview);
        when(userService.verifyUserId(anyLong())).thenReturn(user);

        Review result = reviewService.update(1L, 1L, 1L, dto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getTitle()).isEqualTo(updateReview.getTitle());
        assertThat(result.getContent()).isEqualTo(updateReview.getContent());
        assertThat(result.getSatisfaction()).isEqualTo(updateReview.getSatisfaction());
    }

    @Test
    @DisplayName("리뷰 수정 실패 - 존재하지 않는 리뷰")
    void update_fail_REVIEW_NOT_FOUND() {
        when(reviewRepository.update(anyLong(), anyLong(), any(ReviewRequestDto.class))).thenThrow(new GlobalException(
            ErrorCode.REVIEW_NOT_FOUND,
            ErrorCode.REVIEW_NOT_FOUND.getMessage()
        ));
        when(userService.verifyUserId(anyLong())).thenReturn(user);

        assertThatThrownBy(() -> reviewService.update(1L, 1L, 1L, dto))
            .isInstanceOf(GlobalException.class)
            .hasMessageContaining(ErrorCode.REVIEW_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("리뷰 삭제 성공")
    void delete() {
        when(userService.verifyUserId(loginId)).thenReturn(user);
        when(hospitalRepository.findById(hospitalId)).thenReturn(null);
        doNothing().when(reviewRepository).delete(reviewId, loginId);

        String result = reviewService.delete(hospitalId, reviewId, loginId);

        assertThat(result).isEqualTo("Review deleted");
        verify(userService).verifyUserId(loginId);
        verify(hospitalRepository).findById(hospitalId);
        verify(reviewRepository).delete(reviewId, loginId);
    }

    @Test
    @DisplayName("리뷰 삭제 실패 - 존재하지 않는 병원")
    void delete_fail_HOSPITAL_NOT_FOUND() {
        when(userService.verifyUserId(loginId)).thenReturn(user);
        doThrow(new GlobalException(
            ErrorCode.HOSPITAL_NOT_FOUND,
            ErrorCode.HOSPITAL_NOT_FOUND.getMessage())).when(hospitalRepository).findById(hospitalId);

        assertThatThrownBy(() -> reviewService.delete(hospitalId, reviewId, loginId))
            .isInstanceOf(GlobalException.class)
            .hasMessage(ErrorCode.HOSPITAL_NOT_FOUND.getMessage());

        verify(userService).verifyUserId(loginId);
        verify(hospitalRepository).findById(hospitalId);
        verify(reviewRepository, never()).delete(reviewId, loginId);
    }

    @Test
    @DisplayName("리뷰 조회(단건) 성공")
    void getReview() {
        when(hospitalRepository.findById(hospitalId)).thenReturn(null);
        when(reviewRepository.findById(reviewId)).thenReturn(review);

        Review result = reviewService.findByReviewId(hospitalId, reviewId);

        assertThat(result).isEqualTo(review);
        assertThat(result.getId()).isEqualTo(reviewId);
        assertThat(result.getTitle()).isEqualTo(review.getTitle());
        assertThat(result.getContent()).isEqualTo(review.getContent());
        assertThat(result.getSatisfaction()).isEqualTo(review.getSatisfaction());
    }

    @Test
    @DisplayName("리뷰 조회(전체) 성공")
    void getReviews() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Review> reviewsPage = new PageImpl<>(Collections.singletonList(review), pageable, 1);

        when(reviewRepository.findAll(pageable)).thenReturn(reviewsPage.getContent());

        Page<Review> result = reviewService.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst()).isEqualTo(review);
        verify(reviewRepository).findAll(pageable);
    }

}