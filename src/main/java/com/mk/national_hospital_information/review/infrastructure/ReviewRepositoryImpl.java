package com.mk.national_hospital_information.review.infrastructure;

import com.mk.national_hospital_information.review.application.interfaces.ReviewRepository;
import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.infrastructure.entity.ReviewEntity;
import com.mk.national_hospital_information.review.infrastructure.jpa.ReviewJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}
