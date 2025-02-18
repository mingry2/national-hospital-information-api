package com.mk.national_hospital_information.review.comment.application.interfaces;

public interface CommentLikeRepository {

    void toggleCommentLike(Long loginId, Long findCommentId);
}
