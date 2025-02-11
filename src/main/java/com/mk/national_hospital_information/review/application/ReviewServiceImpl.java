package com.mk.national_hospital_information.review.application;

import com.mk.national_hospital_information.hospital.application.interfaces.HospitalRepository;
import com.mk.national_hospital_information.hospital.domain.Hospital;
import com.mk.national_hospital_information.review.application.interfaces.ReviewRepository;
import com.mk.national_hospital_information.review.application.interfaces.ReviewService;
import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.presentation.dto.ReviewAddRequestDto;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import com.mk.national_hospital_information.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final HospitalRepository hospitalRepository;

    private final UserService userService;

    @Override
    public Review add(Long hospitalId, ReviewAddRequestDto reviewAddRequestDto) {
        // 유저가 존재하는지 검증
        User findUser = userService.verifyUserId(reviewAddRequestDto.userId());
        // 병원이 존재하는지 검증
        Hospital findHospital = hospitalRepository.findById(hospitalId);

        Review review = new Review(reviewAddRequestDto.title(), reviewAddRequestDto.content(), reviewAddRequestDto.satisfaction(), findUser, findHospital);

        return reviewRepository.save(review);
    }
}
