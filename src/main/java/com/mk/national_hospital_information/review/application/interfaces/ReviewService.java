package com.mk.national_hospital_information.review.application.interfaces;

import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.presentation.dto.ReviewAddRequestDto;

public interface ReviewService {

    Review add(Long hospitalId, ReviewAddRequestDto reviewAddRequestDto);

}
