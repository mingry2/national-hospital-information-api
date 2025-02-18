package com.mk.national_hospital_information.review.comment.application.interfaces;

import com.mk.national_hospital_information.review.comment.domain.Comment;
import com.mk.national_hospital_information.review.comment.presentation.dto.CommentRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CommentRepository {

    Comment save(Comment comment);
    Comment findById(Long commentId);
    Comment update(Long commentId, Long loginId, CommentRequestDto commentUpdateRequestDto);
    void delete(Long commentId, Long loginId);
    List<Comment> findAll(Pageable pageable);
}
