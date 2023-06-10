package com.sendkite.teatapp.user.infrastructure;

import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "address")
    private String address;

    @Column(name = "certification_code")
    private String certificationCode;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "last_login_at")
    private Long lastLoginAt;

    public static UserEntity fromDomain(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.id = user.getId();
        userEntity.email = user.getEmail();
        userEntity.address = user.getAddress();
        userEntity.certificationCode = user.getCertificationCode();
        userEntity.nickname = user.getNickname();
        userEntity.status = user.getStatus();
        userEntity.lastLoginAt = user.getLastLoginAt();
        return userEntity;
    }

    public User toDomain() {
        return User.builder()
            .id(id)
            .email(email)
            .nickname(nickname)
            .address(address)
            .certificationCode(certificationCode)
            .status(status)
            .lastLoginAt(lastLoginAt)
            .build();
    }
}
