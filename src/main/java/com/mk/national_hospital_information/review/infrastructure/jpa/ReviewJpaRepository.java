package com.mk.national_hospital_information.review.infrastructure.jpa;

import com.mk.national_hospital_information.review.infrastructure.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {

}
