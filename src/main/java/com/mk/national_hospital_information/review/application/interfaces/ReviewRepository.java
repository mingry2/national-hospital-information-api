package com.mk.national_hospital_information.review.application.interfaces;

import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.presentation.dto.ReviewRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ReviewRepository {

    Review save(Review review);
    Review update(Long oldReviewId, Long writeUserId, ReviewRequestDto reviewUpdateRequestDto);
    void delete(Long reviewId, Long writeUserId);
    Review findById(Long reviewId);
    List<Review> findAll(Pageable pageable);
    void clear();
}
