package com.mk.national_hospital_information.user.application;

import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.user.application.interfaces.UserService;
import com.mk.national_hospital_information.user.presentation.dto.UserJoinRequestDto;
import com.mk.national_hospital_information.user.application.interfaces.UserRepository;
import com.mk.national_hospital_information.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User join(UserJoinRequestDto dto) {
        if (isDuplicated(dto.username())) {
            throw new GlobalException(ErrorCode.DUPLICATED_USER_NAME, ErrorCode.DUPLICATED_USER_NAME.getMessage());
        }

        User user = new User(null, dto.username(), bCryptPasswordEncoder.encode(dto.password()));

        return userRepository.save(user);
    }

    @Override
    public Boolean isDuplicated(String username) {
        try {
            userRepository.findByUsername(username); // 중복 O
            return true;
        } catch (GlobalException e) { // 중복 X
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public User verifyUserId(Long userId) {

        return userRepository.findById(userId);
    }

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long userId) {

        return userRepository.findById(userId);
    }
}
