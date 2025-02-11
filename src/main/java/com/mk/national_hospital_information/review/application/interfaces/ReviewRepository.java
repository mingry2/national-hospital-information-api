package com.mk.national_hospital_information.review.application.interfaces;

import com.mk.national_hospital_information.review.domain.Review;

public interface ReviewRepository {

    Review save(Review review);

}
