package com.mk.national_hospital_information.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class User {

    private Long id;
    private String username;
    private String password;
    private UserRole role;

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = UserRole.ADMIN;
    }

}
