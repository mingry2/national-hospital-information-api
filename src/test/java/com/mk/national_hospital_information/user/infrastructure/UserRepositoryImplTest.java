package com.mk.national_hospital_information.user.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.config.AbstractMySqlTestContainers;
import com.mk.national_hospital_information.user.application.interfaces.UserRepository;
import com.mk.national_hospital_information.user.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

class UserRepositoryImplTest extends AbstractMySqlTestContainers {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private User user;

    @BeforeEach
    void init() {
        user = new User(1L, "testUser", "password");
    }

    @AfterEach
    void clear() {
        jdbcTemplate.execute("TRUNCATE TABLE user");
    }

    @Test
    @DisplayName("유저 저장 성공")
    void save() {
        User savedUser = userRepository.save(user);

        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(savedUser.getRole()).isEqualTo(user.getRole());
    }

    @Test
    @DisplayName("userId로 유저 조회 성공")
    void findById_success() {
        User savedUser = userRepository.save(user);
        User findUser = userRepository.findById(savedUser.getId());

        assertThat(findUser.getId()).isEqualTo(savedUser.getId());
        assertThat(findUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(findUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(findUser.getRole()).isEqualTo(user.getRole());
    }

    @Test
    @DisplayName("userId로 유저 조회 실패 - 유저 없음(savedUserId != otherUserId")
    void findById_failure() {
        Long otherUserId = 2L;
        userRepository.save(user); // savedUserid = 1L

        assertThatThrownBy(() -> userRepository.findById(otherUserId))
            .isInstanceOf(GlobalException.class)
            .hasMessage(ErrorCode.ID_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("username으로 유저 조회 성공")
    void findByUsername_success() {
        User savedUser = userRepository.save(user);
        User findUser = userRepository.findByUsername(savedUser.getUsername());

        assertThat(findUser.getId()).isEqualTo(savedUser.getId());
        assertThat(findUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(findUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(findUser.getRole()).isEqualTo(user.getRole());
    }

    @Test
    @DisplayName("username으로 유저 조회 실패 - 유저 없음(savedUsername != otherUsername")
    void findByUsername_failure() {
        String otherUsername = "otherUsername";
        userRepository.save(user); // savedUsername = "testUser"

        assertThatThrownBy(() -> userRepository.findByUsername(otherUsername))
            .isInstanceOf(GlobalException.class)
            .hasMessage(ErrorCode.USERNAME_NOT_FOUND.getMessage());
    }

}