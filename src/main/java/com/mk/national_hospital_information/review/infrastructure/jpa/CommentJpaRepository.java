package com.mk.national_hospital_information.review.infrastructure.jpa;

import com.mk.national_hospital_information.review.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {

}
