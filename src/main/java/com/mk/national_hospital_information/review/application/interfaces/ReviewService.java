package com.mk.national_hospital_information.review.application.interfaces;

import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.presentation.dto.ReviewRequestDto;

public interface ReviewService {

    Review save(Long hospitalId, Long userId, ReviewRequestDto reviewRequestDto);
    Review update(Long hospitalId, Long reviewId, Long loginId, ReviewRequestDto requestDto);
    String delete(Long hospitalId, Long reviewId, Long writeUserId);
}
