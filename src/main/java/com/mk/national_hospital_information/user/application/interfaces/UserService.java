package com.mk.national_hospital_information.user.application.interfaces;

import com.mk.national_hospital_information.user.domain.User;
import com.mk.national_hospital_information.user.presentation.dto.UserRequestDto;

public interface UserService {

    User join(UserRequestDto userRequestDto);
    Boolean isDuplicated(String username);
    User verifyUserId(Long userId);
    User findByUsername(String username);
    User findById(Long userId);
    String updateUserRole(long userId, String userRole);
}
