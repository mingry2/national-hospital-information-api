package com.mk.national_hospital_information.user.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.user.domain.User;
import com.mk.national_hospital_information.user.infrastructure.UserRepositoryImpl;
import com.mk.national_hospital_information.user.infrastructure.entity.UserEntity;
import com.mk.national_hospital_information.user.infrastructure.jpa.UserJpaRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private UserRepositoryImpl userRepositoryImpl;

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void init() {
        user = new User(1L, "testUser", "testUserPassword");
        userEntity = new UserEntity(user);
    }

    @Test
    @DisplayName("성공 - 회원 저장")
    void save_success() {
        // given
        when(userJpaRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        // when
        User result = userRepositoryImpl.save(user);

        // then
        assertEquals(user.getUsername(), result.getUsername());
        verify(userJpaRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("성공 - Id로 회원 찾기")
    @Transactional
    void findById_success() {
        // given
        when(userJpaRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        // when
        User result = userRepositoryImpl.findById(1L);
        System.out.println("result.toString() = " + result.toString());

        // then
        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        verify(userJpaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("실패 - Id로 회원 찾기")
    void findById_fail() {
        // given
        when(userJpaRepository.findById(1L)).thenReturn(Optional.empty());

        // when, then

        GlobalException exception = assertThrows(GlobalException.class,
            () -> userRepositoryImpl.findById(1L));
        assertEquals(ErrorCode.ID_NOT_FOUND, exception.getErrorCode());
        verify(userJpaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("성공 - Username으로 회원 찾기")
    void findByUsername_success() {
        assertDoesNotThrow(() -> userJpaRepository.findByUsername("testUser"));
    }

}