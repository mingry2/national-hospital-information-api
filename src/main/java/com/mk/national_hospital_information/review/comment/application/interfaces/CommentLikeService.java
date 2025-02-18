package com.mk.national_hospital_information.review.comment.application.interfaces;

public interface CommentLikeService {

    String toggleCommentLike(Long loginId, Long commentId);
}
