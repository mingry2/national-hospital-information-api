package com.mk.national_hospital_information.review.comment.infrastructure.jpa;

import com.mk.national_hospital_information.review.comment.infrastructure.entity.CommentLikeEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLikeEntity, Long> {

    Optional<CommentLikeEntity> findByUserIdAndCommentId(Long loginId, Long commentId);
}
