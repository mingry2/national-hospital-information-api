package com.mk.national_hospital_information.user.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.user.application.interfaces.UserRepository;
import com.mk.national_hospital_information.user.presentation.dto.UserJoinRequestDto;
import com.mk.national_hospital_information.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @InjectMocks
    private UserService userService;
    
    private UserJoinRequestDto dto;
    
    @BeforeEach
    void init() {
        dto = new UserJoinRequestDto("testUser", "passwordTestUser");
    }
    
    @Test
    @DisplayName("성공 - 회원가입")
    void givenUser_whenJoinUser_thenSuccess() {
        // given
        doThrow(new GlobalException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage())).when(userRepository).findByUsername(dto.username());
        User savedUser = new User(1L, dto.username(), "encodedPassword");

        when(bCryptPasswordEncoder.encode(dto.password())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // when
        User result = userService.join(dto);

        // then
        assertEquals(savedUser.getUsername(), result.getUsername());
        assertEquals(savedUser.getPassword(), result.getPassword());
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    @DisplayName("실패 - 아이디 중복")
    void givenDuplicateUser_whenJoinUser_thenThrowException() {
        // given
        when(userRepository.findByUsername(dto.username())).thenReturn(new User(1L, dto.username(), dto.password()));

        // when, then
        GlobalException exception = assertThrows(GlobalException.class, () -> userService.join(dto));
        assertEquals(ErrorCode.DUPLICATED_USER_NAME, exception.getErrorCode());
        verify(userRepository, never()).save(any(User.class));
    }
}