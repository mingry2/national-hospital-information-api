package com.mk.national_hospital_information.user.application.interfaces;

import com.mk.national_hospital_information.user.domain.User;
import com.mk.national_hospital_information.user.presentation.dto.UserJoinRequestDto;

public interface UserService {

    User join(UserJoinRequestDto userJoinRequestDto);
    Boolean isDuplicated(String username);
    User verifyUserId(Long userId);

}
