package com.mk.national_hospital_information.review.infrastructure.jpa;

import com.mk.national_hospital_information.review.infrastructure.entity.ReviewLikeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeJpaRepository extends JpaRepository<ReviewLikeEntity, Long> {
    Optional<ReviewLikeEntity> findByUserIdAndReviewId(Long userId, Long reviewId);

}
