package com.mk.national_hospital_information.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.user.application.interfaces.UserRepository;
import com.mk.national_hospital_information.user.domain.User;
import com.mk.national_hospital_information.user.presentation.dto.UserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserRequestDto dto;


    @BeforeEach
    void init() {
        user = new User(1L, "testUser", "password");
        dto = new UserRequestDto("testUser", "password");
    }

    @Test
    @DisplayName("join 성공")
    void join() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(bCryptPasswordEncoder.encode(dto.password())).thenReturn("encodedPassword");
        when(userRepository.findByUsername(user.getUsername())).thenThrow(
            new GlobalException(ErrorCode.USERNAME_NOT_FOUND,
                ErrorCode.USERNAME_NOT_FOUND.getMessage())); // username 중복 X

        User result = userService.join(dto);

        assertNotNull(result);
        assertEquals(result.getUsername(), dto.username());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("join 실패 - username 중복")
    void join_fail_duplicate_username() {
        when(userRepository.findByUsername(dto.username())).thenReturn(user); // username 중복

        assertThatThrownBy(() -> userService.join(dto))
            .isInstanceOf(GlobalException.class)
            .hasMessageContaining(ErrorCode.DUPLICATED_USER_NAME.getMessage());
    }

    @Test
    @DisplayName("중복 유저 확인 - 중복O(Ture)")
    void isDuplicated_true() {
        when(userRepository.findByUsername(dto.username())).thenReturn(user); // username 중복

        Boolean result = userService.isDuplicated(user.getUsername());

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("중복 유저 확인 - 중복X(False")
    void isDuplicated_false() {
        when(userRepository.findByUsername(dto.username())).thenThrow(
            new GlobalException(ErrorCode.USERNAME_NOT_FOUND,
                ErrorCode.USERNAME_NOT_FOUND.getMessage())
        );

        Boolean result = userService.isDuplicated(user.getUsername());

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("userId로 조회")
    void findById() {
        when(userRepository.findById(user.getId())).thenReturn(user);

        User result = userService.findById(user.getId());

        assertThat(result).isEqualTo(user);
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    @DisplayName("username으로 조회")
    void findByUsername() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

        User result = userService.findByUsername(user.getUsername());

        assertThat(result).isEqualTo(user);
        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    @DisplayName("userId 검증")
    void verifyUserId() {
        when(userRepository.findById(user.getId())).thenReturn(user);

        User result = userService.verifyUserId(user.getId());

        assertThat(result).isEqualTo(user);
        verify(userRepository, times(1)).findById(user.getId());
    }

}