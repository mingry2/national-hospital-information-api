package com.mk.national_hospital_information.review.comment.application;

import com.mk.national_hospital_information.review.comment.application.interfaces.CommentRepository;
import com.mk.national_hospital_information.review.comment.application.interfaces.CommentService;
import com.mk.national_hospital_information.review.application.interfaces.ReviewService;
import com.mk.national_hospital_information.review.comment.domain.Comment;
import com.mk.national_hospital_information.review.comment.presentation.dto.CommentRequestDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final ReviewService reviewService;
    private final CommentRepository commentRepository;

    @Override
    public Comment save(Long reviewId, Long loginId, CommentRequestDto commentAddRequestDto) {
        reviewService.findById(reviewId);

        Comment comment = new Comment(commentAddRequestDto.content(), loginId, reviewId);

        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment update(Long reviewId, Long commentId, Long loginId, CommentRequestDto commentUpdateRequestDto) {
        reviewService.findById(reviewId);

        return commentRepository.update(commentId, loginId, commentUpdateRequestDto);
    }

    @Override
    public String delete(Long reviewId, Long commentId, Long loginId) {
        reviewService.findById(reviewId);

        commentRepository.delete(commentId, loginId);

        return "Comment deleted";
    }

    @Override
    public Comment findComment(Long reviewId, Long commentId) {
        reviewService.findById(reviewId);

        return commentRepository.findById(commentId);
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        List<Comment> comments = commentRepository.findAll(pageable);

        return new PageImpl<>(comments, pageable, comments.size());
    }
}
