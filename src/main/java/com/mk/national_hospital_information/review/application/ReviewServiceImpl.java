package com.mk.national_hospital_information.review.application;

import com.mk.national_hospital_information.hospital.application.interfaces.HospitalRepository;
import com.mk.national_hospital_information.review.application.interfaces.ReviewRepository;
import com.mk.national_hospital_information.review.application.interfaces.ReviewService;
import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.presentation.dto.ReviewRequestDto;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final HospitalRepository hospitalRepository;

    private final UserService userService;

    @Override
    public Review save(Long hospitalId, Long loginId, ReviewRequestDto reviewAddRequestDto) {
        validate(hospitalId, loginId);

        Review review = new Review(hospitalId, loginId, reviewAddRequestDto);

        return reviewRepository.save(review);
    }

    @Override
    @Transactional
    public Review update(Long hospitalId, Long reviewId, Long loginId, ReviewRequestDto reviewUpdateRequestDto) {
        validate(hospitalId, loginId);
        // 리뷰가 존재하는지 검증
        reviewRepository.findById(reviewId);

        Review updateReview = new Review(reviewId, reviewUpdateRequestDto);

        return reviewRepository.update(reviewId, loginId, updateReview);
    }

    @Override
    public String delete(Long hospitalId, Long reviewId, Long loginId) {
        validate(hospitalId, loginId);
        // 리뷰가 존재하는지 검증
        reviewRepository.findById(reviewId);

        reviewRepository.delete(reviewId, loginId);

        return "Review deleted";
    }

    public void validate(Long hospitalId, Long loginId) {
        // 유저가 존재하는지 검증
        userService.verifyUserId(loginId);
        // 병원이 존재하는지 검증
        hospitalRepository.findById(hospitalId);
    }

}
