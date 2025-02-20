package com.mk.national_hospital_information.user.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import com.mk.national_hospital_information.user.domain.User;
import com.mk.national_hospital_information.user.presentation.dto.UserRequestDto;
import com.mk.national_hospital_information.user.presentation.dto.UserJoinResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @Test
    @DisplayName("회원가입 테스트")
    void joinTest() throws Exception {
        UserRequestDto requestDto = new UserRequestDto("testUser", "password");
        User user = new User(1L, "testUser");
        Mockito.when(userService.join(any(UserRequestDto.class))).thenReturn(user);

        UserJoinResponseDto responseDto = new UserJoinResponseDto(user.getId(), user.getUsername());

        mockMvc.perform(post("/api/v1/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
            .andExpect(jsonPath("$.result.userId").value(responseDto.userId()))
            .andExpect(jsonPath("$.result.username").value(responseDto.username()));
    }

    @Test
    @DisplayName("아이디 중복 체크 테스트")
    @WithMockUser(username = "testUser", roles = "USER")
    void checkUsernameTest() throws Exception {
        String username = "testUser";
        Mockito.when(userService.isDuplicated(username)).thenReturn(true);

        mockMvc.perform(get("/api/v1/user/check-username/{username}", username))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
            .andExpect(jsonPath("$.result.isDuplicated").value(true));
    }

}