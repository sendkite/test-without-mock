package com.sendkite.teatapp.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.sendkite.teatapp.common.domain.exception.CertificationCodeNotMatchedException;
import com.sendkite.teatapp.mock.TestClockHolder;
import com.sendkite.teatapp.mock.TestUuidHolder;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void UserCreate로_객체_생성() {
        // given
        UserCreate userCreate = UserCreate.builder()
            .email("hello3@hello.com")
            .nickname("hello3")
            .address("Seoul")
            .build();

        // when
        User user = User.from(userCreate, new TestUuidHolder("aaaa-aaaaa-aaaaa-aaaaaaa"));

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("hello3@hello.com");
        assertThat(user.getNickname()).isEqualTo("hello3");
        assertThat(user.getAddress()).isEqualTo("Seoul");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaa-aaaaa-aaaaa-aaaaaaa");
    }

    @Test
    void UserUpdate_객체로_데이터를_업데이트() {
        // given
        User user = User.builder()
            .id(1L)
            .email("hello@hello.com")
            .nickname("hello")
            .address("Seoul")
            .status(UserStatus.ACTIVE)
            .lastLoginAt(123L)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .build();

        UserUpdate userUpdate = UserUpdate.builder()
            .nickname("hello3-change")
            .address("Donghae")
            .build();

        // when
        user = user.update(userUpdate);

        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("hello@hello.com");
        assertThat(user.getNickname()).isEqualTo("hello3-change");
        assertThat(user.getAddress()).isEqualTo("Donghae");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
        assertThat(user.getLastLoginAt()).isEqualTo(123L);
    }

    @Test
    void 로그인_성공_로그인_시간_변경() {
        // given
        User user = User.builder()
            .id(1L)
            .email("hello@hello.com")
            .nickname("hello")
            .address("Seoul")
            .status(UserStatus.ACTIVE)
            .lastLoginAt(123L)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .build();

        // when
        user = user.login(new TestClockHolder(16784939393L));

        // then
        assertThat(user.getLastLoginAt()).isEqualTo(16784939393L);
    }

    @Test
    void 인증코드로_계정_활성화() {
        // given
        User user = User.builder()
            .id(1L)
            .email("hello@hello.com")
            .nickname("hello")
            .address("Seoul")
            .status(UserStatus.PENDING)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .build();

        // when
        user = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        // then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 잘못된_인증_코드_에러() {
        // given
        User user = User.builder()
            .id(1L)
            .email("hello@hello.com")
            .nickname("hello")
            .address("Seoul")
            .status(UserStatus.PENDING)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .build();

        // when
        // then
        assertThatThrownBy(() -> user.certificate("wrong-code"))
            .isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}