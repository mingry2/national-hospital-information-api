package com.mk.national_hospital_information.user.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.user.domain.User;
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

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void init() {
        user = new User(1L, "testUser", "password");
        userEntity = new UserEntity(1L, user.getUsername(), user.getPassword(), user.getRole());
    }

    @Test
    @DisplayName("user 저장 성공")
    void save() {
        when(userJpaRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        User result = userRepository.save(user);

        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
        assertThat(result.getRole()).isEqualTo(user.getRole());
    }

    @Test
    @DisplayName("userId로 user 찾기 성공")
    void findById_success() {
        when(userJpaRepository.findById(user.getId())).thenReturn(Optional.of(userEntity));

        User result = userRepository.findById(user.getId());

        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
        assertThat(result.getRole()).isEqualTo(user.getRole());
    }

    @Test
    @DisplayName("userId로 user 찾기 실패")
    void findById_failure() {
        when(userJpaRepository.findById(user.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userRepository.findById(user.getId()))
            .isInstanceOf(GlobalException.class)
            .hasMessage(ErrorCode.ID_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("username으로 user 찾기 성공")
    void findByUsername_success() {
        when(userJpaRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(userEntity));

        User result = userRepository.findByUsername(user.getUsername());

        assertThat(result.getUsername()).isEqualTo(user.getUsername());
        assertThat(result.getPassword()).isEqualTo(user.getPassword());
        assertThat(result.getRole()).isEqualTo(user.getRole());
    }

    @Test
    @DisplayName("username으로 user 찾기 실패")
    void findByUsername_failure() {
        when(userJpaRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userRepository.findByUsername(user.getUsername()))
            .isInstanceOf(GlobalException.class)
            .hasMessage(ErrorCode.USERNAME_NOT_FOUND.getMessage());
    }

}