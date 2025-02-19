package com.mk.national_hospital_information.user.infrastructure.entity;

import com.mk.national_hospital_information.user.domain.User;
import com.mk.national_hospital_information.user.domain.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.context.support.BeanDefinitionDsl.Role;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Getter
@SQLRestriction("deleted_at IS NULL")
public class UserEntity extends UserBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public UserEntity(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    public UserEntity(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Entity -> Domain
    public User toUser() {
        return new User(
            this.id,
            this.username,
            this.password,
            this.role);
    }

    public void updateRole(UserRole oldRole) {
        this.role = oldRole;
    }
}
