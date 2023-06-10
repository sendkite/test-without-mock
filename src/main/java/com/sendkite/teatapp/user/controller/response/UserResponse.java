package com.sendkite.teatapp.user.controller.response;

import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserStatus;
import com.sendkite.teatapp.user.infrastructure.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String email;
    private String nickname;
    private UserStatus status;
    private Long lastLoginAt;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .status(user.getStatus())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
