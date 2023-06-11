package com.sendkite.teatapp.user.controller.response;

import static org.assertj.core.api.Assertions.assertThat;

import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

class UserResponseTest {

    @Test
    void User로_응답_반환() {
        // given
        User user = User.builder()
            .id(1L)
            .email("hello2@naver.com")
            .nickname("hello2")
            .address("Seoul")
            .status(UserStatus.ACTIVE)
            .lastLoginAt(100L)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .build();

        // when
        UserResponse userResponse = UserResponse.from(user);

        // then
        assertThat(userResponse.getId()).isEqualTo(1L);
        assertThat(userResponse.getEmail()).isEqualTo("hello2@naver.com");
        assertThat(userResponse.getNickname()).isEqualTo("hello2");
        assertThat(userResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(userResponse.getLastLoginAt()).isEqualTo(100L);
    }
}