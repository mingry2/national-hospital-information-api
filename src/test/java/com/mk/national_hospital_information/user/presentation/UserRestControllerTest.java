package com.mk.national_hospital_information.user.presentation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import com.mk.national_hospital_information.user.domain.User;
import com.mk.national_hospital_information.user.presentation.dto.UserJoinRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


//TODO : @WebMvcTest 에러 확인
@SpringBootTest
class UserRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private UserJoinRequestDto userJoinRequestDto;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = new User(1L, "userD", "userAPassword");
        userJoinRequestDto = new UserJoinRequestDto(user.getUsername(), user.getPassword());
    }

    @Test
    @DisplayName("성공 - 회원가입")
    void join_success() throws Exception {
        when(userService.join(any(UserJoinRequestDto.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/user/join")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userJoinRequestDto)).with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result.userId").value(user.getId()))
            .andExpect(jsonPath("$.result.username").value(user.getUsername()));
    }

    @Test
    @DisplayName("실패 - 회원가입 (중복된 아이디)")
    void join_fail_duplicated_id() throws Exception {
        when(userService.join(any(UserJoinRequestDto.class))).thenThrow(new GlobalException(
            ErrorCode.DUPLICATED_USER_NAME,
            ErrorCode.DUPLICATED_USER_NAME.getMessage()));

        mockMvc.perform(post("/api/v1/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userJoinRequestDto)))
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.result.errorCode").value("DUPLICATED_USER_NAME"))
            .andExpect(jsonPath("$.result.message").value("DUPLICATED USERNAME"));
    }

    @Test
    @DisplayName("성공 - 아이디 중복 확인")
    void check_username_success() throws Exception {
        when(userService.isDuplicated("userA")).thenReturn(true);

        mockMvc.perform(get("/api/v1/user/check-username/userA"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result.isDuplicated").value(true));
    }

    @Test
    @DisplayName("실패 - 아이디 중복 확인 (중복된 아이디)")
    void check_username_fail_duplicated_id() throws Exception {
        when(userService.isDuplicated("userA")).thenReturn(false);

        mockMvc.perform(get("/api/v1/user/check-username/userA"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result.isDuplicated").value(false));
    }

}