package com.mk.national_hospital_information.review.application;

import com.mk.national_hospital_information.hospital.application.interfaces.HospitalRepository;
import com.mk.national_hospital_information.hospital.infrastructure.jpa.HospitalJpaRepository;
import com.mk.national_hospital_information.review.application.interfaces.ReviewRepository;
import com.mk.national_hospital_information.review.application.interfaces.ReviewService;
import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.presentation.dto.ReviewRequestDto;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final HospitalRepository hospitalRepository;

    private final UserService userService;
    private final HospitalJpaRepository hospitalJpaRepository;

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

        Review updateReview = new Review(reviewId, reviewUpdateRequestDto);

        return reviewRepository.update(reviewId, loginId, updateReview);
    }

    @Override
    public String delete(Long hospitalId, Long reviewId, Long loginId) {
        validate(hospitalId, loginId);

        reviewRepository.delete(reviewId, loginId);

        return "Review deleted";
    }

    @Override
    public Review findByReviewId(Long hospitalId, Long reviewId) {
        // 병원이 존재하는지 검증
        hospitalRepository.findById(hospitalId);

        return reviewRepository.findById(reviewId);
    }

    @Override
    public Page<Review> findAll(Pageable pageable) {
        List<Review> reviews = reviewRepository.findAll(pageable);

        return new PageImpl<>(reviews, pageable, reviews.size());
    }

    public void validate(Long hospitalId, Long loginId) {
        // 유저가 존재하는지 검증
        userService.verifyUserId(loginId);
        // 병원이 존재하는지 검증
        hospitalRepository.findById(hospitalId);
    }

}
