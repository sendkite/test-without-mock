package com.sendkite.teatapp.user.controller.response;

import static org.assertj.core.api.Assertions.assertThat;

import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

class MyProfileResponseTest {

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
        MyProfileResponse myProfileResponse = MyProfileResponse.from(user);

        // then
        assertThat(myProfileResponse.getId()).isEqualTo(1L);
        assertThat(myProfileResponse.getEmail()).isEqualTo("hello2@naver.com");
        assertThat(myProfileResponse.getNickname()).isEqualTo("hello2");
        assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(100L);
    }

}