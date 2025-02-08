package com.mk.national_hospital_information.user.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("User 객체 생성 테스트")
    void givenIdUsernamePassword_whenCreateUser_thenSuccess() {
        // given
        Long id = 1L;
        String username = "username";
        String password = "password";

        // when
        User user = new User(id, username, password);

        // then
        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }

}