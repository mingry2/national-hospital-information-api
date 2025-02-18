package com.mk.national_hospital_information.review.application;

import com.mk.national_hospital_information.review.application.interfaces.ReviewLikeRepository;
import com.mk.national_hospital_information.review.application.interfaces.ReviewLikeService;
import com.mk.national_hospital_information.review.application.interfaces.ReviewRepository;
import com.mk.national_hospital_information.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewLikeServiceImpl implements ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public String toggleReviewLike(Long loginId, Long reviewId) {
        Review findReview = reviewRepository.findById(reviewId);

        reviewLikeRepository.toggleReviewLike(loginId, findReview.getId());

        return "Review like/unlike Successfully";
    }
}
