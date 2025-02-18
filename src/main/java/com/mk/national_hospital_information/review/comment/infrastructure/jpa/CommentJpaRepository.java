package com.mk.national_hospital_information.review.comment.infrastructure.jpa;

import com.mk.national_hospital_information.review.comment.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {

}
