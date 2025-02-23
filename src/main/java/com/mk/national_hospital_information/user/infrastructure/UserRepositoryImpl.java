package com.mk.national_hospital_information.user.infrastructure;

import com.mk.national_hospital_information.common.exception.GlobalException;
import com.mk.national_hospital_information.common.exception.ErrorCode;
import com.mk.national_hospital_information.user.application.interfaces.UserRepository;
import com.mk.national_hospital_information.user.domain.User;
import com.mk.national_hospital_information.user.domain.UserRole;
import com.mk.national_hospital_information.user.infrastructure.entity.UserEntity;
import com.mk.national_hospital_information.user.infrastructure.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity(user);

        return userJpaRepository.save(userEntity).toUser();
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

    @Override
    public User findByUsername(String username) {
        UserEntity userEntity = userJpaRepository.findByUsername(username)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.USERNAME_NOT_FOUND,
                ErrorCode.USERNAME_NOT_FOUND.getMessage()
            ));

        return userEntity.toUser();
    }

    @Override
    public void userRoleUpdate(long userId, UserRole newUserRole) {
        UserEntity userEntity = userJpaRepository.findById(userId)
            .orElseThrow(() -> new GlobalException(
                ErrorCode.ID_NOT_FOUND,
                ErrorCode.ID_NOT_FOUND.getMessage()
            ));

        userEntity.updateRole(newUserRole);
    }

}
