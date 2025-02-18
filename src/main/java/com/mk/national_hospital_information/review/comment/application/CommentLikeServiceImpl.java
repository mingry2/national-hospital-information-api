package com.mk.national_hospital_information.review.comment.application;

import com.mk.national_hospital_information.review.comment.application.interfaces.CommentLikeRepository;
import com.mk.national_hospital_information.review.comment.application.interfaces.CommentLikeService;
import com.mk.national_hospital_information.review.comment.application.interfaces.CommentRepository;
import com.mk.national_hospital_information.review.comment.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;

    @Override
    public String toggleCommentLike(Long loginId, Long commentId) {
        Comment findComment = commentRepository.findById(commentId);

        commentLikeRepository.toggleCommentLike(loginId, findComment.getId());

        return "Comment like/unlike Successfully";
    }
}
