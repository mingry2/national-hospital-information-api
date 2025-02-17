package com.mk.national_hospital_information.review.application.interfaces;

import com.mk.national_hospital_information.review.domain.Comment;
import com.mk.national_hospital_information.review.presentation.dto.CommentRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    Comment save(Long reviewId, Long loginId, CommentRequestDto commentRequestDto);
    Comment update(Long reviewId, Long commentId, Long loginId, CommentRequestDto commentUpdateRequestDto);
    String delete(Long reviewId, Long commentId, Long loginId);
    Comment findComment(Long reviewId, Long commentId);
    Page<Comment> findAll(Pageable pageable);
}
