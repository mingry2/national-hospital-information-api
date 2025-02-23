package com.mk.national_hospital_information.user.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.national_hospital_information.config.AbstractMySqlTestContainers;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import com.mk.national_hospital_information.user.domain.User;
import com.mk.national_hospital_information.user.presentation.dto.UserRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
class UserRestControllerTest extends AbstractMySqlTestContainers {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원가입 api 테스트")
    void joinTest() throws Exception {
        User user = new User(1L, "testUser", "password");
        UserRequestDto userJoinRequestDto = new UserRequestDto("testUser", "password");
        when(userService.join(any(UserRequestDto.class))).thenReturn(user);

        mockMvc.perform(post("/api/v1/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userJoinRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
            .andExpect(jsonPath("$.result.userId").value(1L))
            .andExpect(jsonPath("$.result.username").value("testUser"));
    }

    @Test
    @DisplayName("아이디 중복 체크 api 테스트")
    @WithMockUser(username = "testUser", roles = "USER")
    void checkUsernameTest() throws Exception {
        String checkUseranme = "testUser";
        when(userService.isDuplicated(checkUseranme)).thenReturn(true);

        mockMvc.perform(get("/api/v1/user/check-username/{username}", checkUseranme))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
            .andExpect(jsonPath("$.result.isDuplicated").value(true));
    }

}