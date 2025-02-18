package com.mk.national_hospital_information.review.comment.infrastructure;

import com.mk.national_hospital_information.review.comment.application.interfaces.CommentLikeRepository;
import com.mk.national_hospital_information.review.comment.infrastructure.entity.CommentLikeEntity;
import com.mk.national_hospital_information.review.comment.infrastructure.jpa.CommentLikeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentLikeRepositoryImpl implements CommentLikeRepository {

    private final CommentLikeJpaRepository commentLikeJpaRepository;

    @Override
    public void toggleCommentLike(Long loginId, Long commentId) {
        commentLikeJpaRepository.findByUserIdAndCommentId(loginId, commentId)
            .ifPresentOrElse(entity -> {
                entity.setCommentLike(!entity.isCommentLike());
                commentLikeJpaRepository.save(entity);
                },
            () -> commentLikeJpaRepository.save(new CommentLikeEntity(true, loginId, commentId)));
    }
}
