package com.mk.national_hospital_information.review.presentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.review.application.interfaces.ReviewService;
import com.mk.national_hospital_information.review.domain.Review;
import com.mk.national_hospital_information.review.presentation.dto.ReviewRequestDto;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import com.mk.national_hospital_information.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ReviewService reviewService;

    @MockitoBean
    private UserService userService;

    private User user;
    private ReviewRequestDto dto;

    @BeforeEach
    void init() {
        user = new User(1L, "testUser");
        dto = new ReviewRequestDto("testTitle", "testContent", 1L);
    }

    @Test
    @DisplayName("리뷰 등록 성공")
    @WithMockUser(username = "testUser")
    void saveReview() throws Exception {
        Review savedReview = new Review(1L, "testTitle", "testContent", 1L, 1L, 1L);
        given(reviewService.save(anyLong(), anyLong(), any(ReviewRequestDto.class))).willReturn(savedReview);
        given(userService.findByUsername("testUser")).willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/hospital/1/review")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.resultCode").value("SUCCESS"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.reviewId").value(savedReview.getId()))
            .andDo(print());
    }

    @Test
    @DisplayName("리뷰 등록 실패 - 존재하지 않는 병원")
    @WithMockUser(username = "testUser")
    void saveReview_fail_HOSPITAL_NOT_FOUND() throws Exception {
        doThrow(new GlobalException(
            ErrorCode.HOSPITAL_NOT_FOUND,
            ErrorCode.HOSPITAL_NOT_FOUND.getMessage()))
            .when(reviewService).save(anyLong(), anyLong(), any(ReviewRequestDto.class));

        given(userService.findByUsername("testUser")).willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/hospital/999/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(print());
    }

    @Test
    @DisplayName("리뷰 수정 성공")
    @WithMockUser(username = "testUser")
    void updateReview() throws Exception {
        ReviewRequestDto updateDto = new ReviewRequestDto("updateTitle", "updateContent", 1L);
        Review updatedReview = new Review(1L, "updateTitle", "updateContent", 1L, 1L, 1L);

        given(reviewService.update(anyLong(), anyLong(), anyLong(), any(ReviewRequestDto.class))).willReturn(updatedReview);
        given(userService.findByUsername("testUser")).willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/hospital/1/review/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.resultCode").value("SUCCESS"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.reviewId").value(updatedReview.getId()))
            .andDo(print());
    }

    @Test
    @DisplayName("리뷰 수정 실패 - 존재하지 않는 리뷰")
    @WithMockUser(username = "testUser")
    void updateReview_fail_REVIEW_NOT_FOUND() throws Exception {
        doThrow(new GlobalException(
            ErrorCode.REVIEW_NOT_FOUND,
            ErrorCode.REVIEW_NOT_FOUND.getMessage()))
            .when(reviewService).update(anyLong(), anyLong(), anyLong(), any(ReviewRequestDto.class));

        given(userService.findByUsername("testUser")).willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/hospital/1/review/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(print());
    }

    @Test
    @DisplayName("리뷰 삭제 성공")
    @WithMockUser(username = "testUser")
    void deleteReviewTest() throws Exception {
        given(reviewService.delete(anyLong(), anyLong(), anyLong())).willReturn("Review deleted");
        given(userService.findByUsername("testUser")).willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/hospital/1/review/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("Review deleted"))
            .andDo(print());
    }

    @Test
    @DisplayName("리뷰 삭제 실패 - 존재하지 않는 리뷰")
    @WithMockUser(username = "testUser")
    void deleteReviewNotFoundFailureTest() throws Exception {
        doThrow(new GlobalException(
            ErrorCode.REVIEW_NOT_FOUND,
            ErrorCode.REVIEW_NOT_FOUND.getMessage()))
            .when(reviewService).delete(anyLong(), anyLong(), anyLong());

        given(userService.findByUsername("testUser")).willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/hospital/1/review/999"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(print());
    }

    @Test
    @DisplayName("리뷰 (단건)조회 성공")
    @WithMockUser(username = "testUser")
    void getReviewTest() throws Exception {
        Review review = new Review(1L, "testTitle", "testContent", 1L, 1L, 1L);

        given(reviewService.findByReviewId(anyLong(), anyLong())).willReturn(review);
        given(userService.findByUsername("testUser")).willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hospital/1/review/1"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.resultCode").value("SUCCESS"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.reviewId").value(review.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.title").value(review.getTitle()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.result.content").value(review.getContent()))
            .andDo(print());
    }

    @Test
    @DisplayName("리뷰 (단건)조회 실패")
    @WithMockUser(username = "testUser")
    void getReviewNotFoundTest() throws Exception {
        doThrow(new GlobalException(
            ErrorCode.REVIEW_NOT_FOUND,
            ErrorCode.REVIEW_NOT_FOUND.getMessage()))
            .when(reviewService).findByReviewId(anyLong(), anyLong());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hospital/1/review/999"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(print());
    }

}