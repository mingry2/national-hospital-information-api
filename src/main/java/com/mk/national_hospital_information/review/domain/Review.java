package com.mk.national_hospital_information.review.domain;

import com.mk.national_hospital_information.review.presentation.dto.ReviewRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Review {

    private Long id;
    private String title;
    private String content;
    private Long satisfaction;
    private Long userId;
    private Long hospitalId;

    public Review(Long hospitalId, Long userId, ReviewRequestDto reviewRequestDto) {
        this.title = reviewRequestDto.title();
        this.content = reviewRequestDto.content();
        this.satisfaction = reviewRequestDto.satisfaction();
        this.userId = userId;
        this.hospitalId = hospitalId;
    }

    public Review(Long hospitalId, ReviewRequestDto reviewRequestDto) {
        this.title = reviewRequestDto.title();
        this.content = reviewRequestDto.content();
        this.satisfaction = reviewRequestDto.satisfaction();
        this.hospitalId = hospitalId;
    }

    @Override
    public String toString() {
        return "Review{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", satisfaction=" + satisfaction +
            ", userId=" + userId +
            ", hospitalId=" + hospitalId +
            '}';
    }
}
