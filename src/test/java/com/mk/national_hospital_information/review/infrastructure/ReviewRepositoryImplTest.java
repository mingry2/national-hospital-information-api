package com.mk.national_hospital_information.review.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.config.TestAuditorAwareConfig;
import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.infrastructure.entity.ReviewEntity;
import com.mk.national_hospital_information.review.infrastructure.jpa.ReviewJpaRepository;
import com.mk.national_hospital_information.review.presentation.dto.ReviewRequestDto;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestAuditorAwareConfig.class)
class ReviewRepositoryImplTest {

    @InjectMocks
    private ReviewRepositoryImpl reviewRepository;

    @Mock
    private ReviewJpaRepository reviewJpaRepository;

    private ReviewEntity reviewEntity;
    private Review review;

    @BeforeEach
    void init() {
        reviewEntity = new ReviewEntity(1L, "title", "content", 5L, 1L, 1L);
        review = reviewEntity.toReview();
    }

    @Test
    @DisplayName("리뷰 등록 성공")
    void save() {
        when(reviewJpaRepository.save(any(ReviewEntity.class))).thenReturn(reviewEntity);

        Review savedReview = reviewRepository.save(review);

        assertThat(savedReview.getId()).isEqualTo(review.getId());
        assertThat(savedReview.getTitle()).isEqualTo(review.getTitle());
        assertThat(savedReview.getContent()).isEqualTo(review.getContent());
        verify(reviewJpaRepository).save(any(ReviewEntity.class));
    }

    @Test
    @DisplayName("리뷰 수정 성공")
    void update() {
        ReviewRequestDto updateDto = new ReviewRequestDto("update title", "update content", 4L);

        when(reviewJpaRepository.findById(1L)).thenReturn(Optional.of(reviewEntity));

        Review updatedReview = reviewRepository.update(1L, 1L, updateDto);

        assertThat(updatedReview.getId()).isEqualTo(1L);
        assertThat(updatedReview.getTitle()).isEqualTo("update title");
        assertThat(updatedReview.getContent()).isEqualTo("update content");
        verify(reviewJpaRepository).findById(1L);
    }

    @Test
    @DisplayName("리뷰 수정 실패 - 권한 없음(작성자 != 수정자")
    void update_fail_INVALID_USER() {
        ReviewRequestDto updateDto = new ReviewRequestDto("update title", "update content", 4L);

        when(reviewJpaRepository.findById(1L)).thenReturn(Optional.of(reviewEntity));

        assertThatThrownBy(() -> reviewRepository.update(1L, 2L, updateDto))
            .isInstanceOf(GlobalException.class)
            .hasMessageContaining(ErrorCode.INVALID_PERMISSION.getMessage());

        verify(reviewJpaRepository).findById(1L);
    }

    @Test
    @DisplayName("리뷰 삭제 성공")
    void delete() {
        when(reviewJpaRepository.findById(1L)).thenReturn(Optional.of(reviewEntity));

        reviewRepository.delete(1L, 1L);

        assertThat(reviewEntity.getDeletedAt()).isNotNull();
        verify(reviewJpaRepository).save(reviewEntity);
    }

    @Test
    @DisplayName("리뷰 삭제 실패 - 권한 없음(작성자 != 삭제자")
    void delete_fail_INVALID_USER() {
        when(reviewJpaRepository.findById(1L)).thenReturn(Optional.of(reviewEntity));

        assertThatThrownBy(() -> reviewRepository.delete(1L, 2L))
            .isInstanceOf(GlobalException.class)
            .hasMessageContaining(ErrorCode.INVALID_PERMISSION.getMessage());

        verify(reviewJpaRepository, never()).save(any());
    }

    @Test
    @DisplayName("리뷰 조회(단건) 성공")
    void getReview() {
        when(reviewJpaRepository.findById(1L)).thenReturn(Optional.of(reviewEntity));

        Review foundReview = reviewRepository.findById(1L);

        assertThat(foundReview.getId()).isEqualTo(review.getId());
        assertThat(foundReview.getTitle()).isEqualTo(review.getTitle());
        assertThat(foundReview.getContent()).isEqualTo(review.getContent());
        verify(reviewJpaRepository).findById(1L);
    }

    @Test
    @DisplayName("리뷰 조회(단건) 실패 - 존재하지 않는 리뷰")
    void getReview_fail_REVIEW_NOT_FOUND() {
        when(reviewJpaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> reviewRepository.findById(1L))
            .isInstanceOf(GlobalException.class)
            .hasMessageContaining(ErrorCode.REVIEW_NOT_FOUND.getMessage());

        verify(reviewJpaRepository).findById(1L);
    }

    @Test
    @DisplayName("리뷰 조회(전체) 성공")
    void getReviews() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ReviewEntity> page = new PageImpl<>(Collections.singletonList(reviewEntity));

        when(reviewJpaRepository.findAll(pageable)).thenReturn(page);

        List<Review> reviews = reviewRepository.findAll(pageable);

        assertThat(reviews).hasSize(1);
        assertThat(reviews.getFirst().getId()).isEqualTo(review.getId());
        assertThat(reviews.getFirst().getTitle()).isEqualTo(review.getTitle());
        assertThat(reviews.getFirst().getContent()).isEqualTo(review.getContent());
        verify(reviewJpaRepository).findAll(pageable);
    }
}