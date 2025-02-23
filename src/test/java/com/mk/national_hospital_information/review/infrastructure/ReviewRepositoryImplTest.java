package com.mk.national_hospital_information.review.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.config.AbstractMySqlTestContainers;
import com.mk.national_hospital_information.review.application.interfaces.ReviewRepository;
import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.presentation.dto.ReviewRequestDto;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;

class ReviewRepositoryImplTest extends AbstractMySqlTestContainers {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Review review;
    private Long writerUserId;

    @BeforeEach
    void init() {
        writerUserId = 1L;
        review = new Review(1L, "title", "content", 5L, writerUserId, 1L);
    }

    @AfterEach
    void clear() {
        jdbcTemplate.execute("TRUNCATE TABLE review");
    }

    @Test
    @DisplayName("리뷰 등록 성공")
    void save() {
        Review savedReview = reviewRepository.save(review);

        assertThat(savedReview.getId()).isEqualTo(review.getId());
        assertThat(savedReview.getTitle()).isEqualTo(review.getTitle());
        assertThat(savedReview.getContent()).isEqualTo(review.getContent());
    }

    @Test
    @DisplayName("리뷰 수정 성공")
    void update() {
        Review savedReview = reviewRepository.save(review);
        Review oldReview = reviewRepository.findById(savedReview.getId());
        ReviewRequestDto updateDto = new ReviewRequestDto("update title", "update content", 4L);
        Review updatedReview = reviewRepository.update(oldReview.getId(), writerUserId, updateDto);

        assertThat(updatedReview.getId()).isEqualTo(1L);
        assertThat(updatedReview.getTitle()).isEqualTo("update title");
        assertThat(updatedReview.getContent()).isEqualTo("update content");
    }

    @Test
    @DisplayName("리뷰 수정 실패 - 권한 없음(writerUserId != otherUserId")
    void update_fail_INVALID_USER() {
        Long otherUserId = 2L;
        Review savedReview = reviewRepository.save(review);
        Review oldReview = reviewRepository.findById(savedReview.getId());
        ReviewRequestDto updateDto = new ReviewRequestDto("update title", "update content", 4L);

        assertThatThrownBy(() -> reviewRepository.update(oldReview.getId(), otherUserId, updateDto))
            .isInstanceOf(GlobalException.class)
            .hasMessageContaining(ErrorCode.INVALID_PERMISSION.getMessage());
    }

    @Test
    @DisplayName("리뷰 삭제 성공")
    void delete() {
        Review savedReview = reviewRepository.save(review);
        Review findReview = reviewRepository.findById(savedReview.getId());

        reviewRepository.delete(findReview.getId(), writerUserId);

        Assertions.assertThrows(GlobalException.class, () -> reviewRepository.findById(findReview.getId()));
    }

    @Test
    @DisplayName("리뷰 삭제 실패 - 권한 없음(writerUserId != otherUserId")
    void delete_fail_INVALID_USER() {
        Long otherUserId = 2L;
        Review savedReview = reviewRepository.save(review);
        Review findReview = reviewRepository.findById(savedReview.getId());

        assertThatThrownBy(() -> reviewRepository.delete(findReview.getId(), otherUserId))
            .isInstanceOf(GlobalException.class)
            .hasMessageContaining(ErrorCode.INVALID_PERMISSION.getMessage());
    }

    @Test
    @DisplayName("리뷰 조회(단건) 성공")
    void getReview() {
        Review savedReview = reviewRepository.save(review);

        Review findReview = reviewRepository.findById(savedReview.getId());

        assertThat(findReview.getId()).isEqualTo(savedReview.getId());
        assertThat(findReview.getTitle()).isEqualTo(savedReview.getTitle());
        assertThat(findReview.getContent()).isEqualTo(savedReview.getContent());
    }

    @Test
    @DisplayName("리뷰 조회(단건) 실패 - 존재하지 않는 리뷰")
    void getReview_fail_REVIEW_NOT_FOUND() {
        reviewRepository.save(review); // reviewId = 1L

        assertThatThrownBy(() -> reviewRepository.findById(2L))
            .isInstanceOf(GlobalException.class)
            .hasMessageContaining(ErrorCode.REVIEW_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("리뷰 조회(전체) 성공")
    void getReviews() {
        reviewRepository.save(review);
        reviewRepository.save(review);

        Pageable pageable = PageRequest.of(0, 10);

        List<Review> reviews = reviewRepository.findAll(pageable);

        assertThat(reviews).hasSize(2);
        assertThat(reviews.getFirst().getId()).isEqualTo(review.getId());
        assertThat(reviews.getFirst().getTitle()).isEqualTo(review.getTitle());
        assertThat(reviews.getFirst().getContent()).isEqualTo(review.getContent());
    }
}