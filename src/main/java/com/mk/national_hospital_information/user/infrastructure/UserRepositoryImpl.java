package com.mk.national_hospital_information.user.infrastructure;

import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.user.application.interfaces.UserRepository;
import com.mk.national_hospital_information.user.domain.User;
import com.mk.national_hospital_information.user.infrastructure.entity.UserEntity;
import com.mk.national_hospital_information.user.infrastructure.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {

        UserEntity userEntity = new UserEntity(user);
        userEntity = userJpaRepository.save(userEntity);
        return userEntity.toUser();
    }

    @Override
    public User findById(Long id) {

        UserEntity userEntity = userJpaRepository.findById(id)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.ID_NOT_FOUND,
                ErrorCode.ID_NOT_FOUND.getMessage()
            ));
        return userEntity.toUser();
    }

    public User findByUsername(String username) {

        UserEntity userEntity = userJpaRepository.findByUsername(username)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.USERNAME_NOT_FOUND,
                ErrorCode.USERNAME_NOT_FOUND.getMessage()
            ));
        return userEntity.toUser();
    }
}
