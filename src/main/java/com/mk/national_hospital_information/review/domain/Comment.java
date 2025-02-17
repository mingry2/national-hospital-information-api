package com.mk.national_hospital_information.review.domain;

import com.mk.national_hospital_information.review.presentation.dto.CommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Comment {

    private Long id;
    private String content;
    private Long userId;
    private Long reviewId;

    public Comment(String content, Long userId, Long reviewId) {
        this.content = content;
        this.userId = userId;
        this.reviewId = reviewId;
    }

    public Comment(CommentRequestDto commentRequestDto, Long reviewId) {
        this.content = commentRequestDto.content();
        this.reviewId = reviewId;
    }
}
