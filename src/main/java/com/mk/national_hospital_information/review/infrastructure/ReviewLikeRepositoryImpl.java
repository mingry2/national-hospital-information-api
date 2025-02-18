package com.mk.national_hospital_information.review.infrastructure;

import com.mk.national_hospital_information.review.application.interfaces.ReviewLikeRepository;
import com.mk.national_hospital_information.review.infrastructure.entity.ReviewLikeEntity;
import com.mk.national_hospital_information.review.infrastructure.jpa.ReviewLikeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewLikeRepositoryImpl implements ReviewLikeRepository {

    private final ReviewLikeJpaRepository reviewLikeJpaRepository;

    @Override
    public void toggleReviewLike(Long loginId, Long reviewId) {
        reviewLikeJpaRepository.findByUserIdAndReviewId(loginId, reviewId)
            .ifPresentOrElse(entity -> {
                entity.setReviewLike(!entity.isReviewLike());
                reviewLikeJpaRepository.save(entity);
                },
            () -> reviewLikeJpaRepository.save(new ReviewLikeEntity(true, loginId, reviewId)));
    }
}
