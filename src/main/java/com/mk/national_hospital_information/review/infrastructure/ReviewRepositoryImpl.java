package com.mk.national_hospital_information.review.infrastructure;

import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.review.application.interfaces.ReviewRepository;
import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.infrastructure.entity.ReviewEntity;
import com.mk.national_hospital_information.review.infrastructure.jpa.ReviewJpaRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public Review save(Review review) {
        ReviewEntity reviewEntity = new ReviewEntity(review);

        return reviewJpaRepository.save(reviewEntity).toReview();
    }

    @Override
    @Transactional
    public Review update(Long reviewId, Long loginId, Review updateReview) {
        ReviewEntity findReviewEntity = reviewJpaRepository.findById(reviewId)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.REVIEW_NOT_FOUND,
                ErrorCode.REVIEW_NOT_FOUND.getMessage()
            ));

        Long writeUserId = findReviewEntity.getUserId();

        if (!writeUserId.equals(loginId)){
            throw new GlobalException(
                ErrorCode.INVALID_PERMISSION,
                ErrorCode.INVALID_PERMISSION.getMessage()
            );
        }

        findReviewEntity.updateReview(updateReview);

        return findReviewEntity.toReview();
    }

    @Override
    public void delete(Long reviewId, Long loginId) {
        ReviewEntity findReviewEntity = reviewJpaRepository.findById(reviewId)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.REVIEW_NOT_FOUND,
                ErrorCode.REVIEW_NOT_FOUND.getMessage()
            ));

        Long writeUserId = findReviewEntity.getUserId();

        if (!writeUserId.equals(loginId)){
            throw new GlobalException(
                ErrorCode.INVALID_PERMISSION,
                ErrorCode.INVALID_PERMISSION.getMessage()
            );
        }

        findReviewEntity.setDeletedAt(LocalDateTime.now());
        reviewJpaRepository.save(findReviewEntity);
    }

    @Override
    public Review findById(Long reviewId) {
        ReviewEntity reviewEntity = reviewJpaRepository.findById(reviewId)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.REVIEW_NOT_FOUND,
                ErrorCode.REVIEW_NOT_FOUND.getMessage()
            ));

        return reviewEntity.toReview();
    }

    @Override
    public List<Review> findAll(Pageable pageable) {
        Page<ReviewEntity> reviewEntities = reviewJpaRepository.findAll(pageable);

        List<Review> reviews = new ArrayList<>();
        for (ReviewEntity reviewEntity : reviewEntities) {
            reviews.add(reviewEntity.toReview());
        }

        return reviews;
    }

}
