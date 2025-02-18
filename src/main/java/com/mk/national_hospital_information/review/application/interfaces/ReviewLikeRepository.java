package com.mk.national_hospital_information.review.application.interfaces;

public interface ReviewLikeRepository {

    void toggleReviewLike(Long loginId, Long findReviewId);

}
