package com.mk.national_hospital_information.review.presentation.dto;

public record ReviewFindResponseDto(Long hospitalId, Long reviewId, String title, String content, Long satisfaction, Long userId) {

}
