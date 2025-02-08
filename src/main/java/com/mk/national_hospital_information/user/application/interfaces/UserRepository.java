package com.mk.national_hospital_information.user.application.interfaces;

import com.mk.national_hospital_information.user.domain.User;


public interface UserRepository {

    User save(User user);
    User findById(Long id);
    User findByUsername(String username);
}
