package com.sendkite.teatapp.user.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.sendkite.teatapp.common.domain.exception.CertificationCodeNotMatchedException;
import com.sendkite.teatapp.common.domain.exception.ResourceNotFoundException;
import com.sendkite.teatapp.mock.TestContainer;
import com.sendkite.teatapp.user.controller.port.UserReadService;
import com.sendkite.teatapp.user.controller.response.MyProfileResponse;
import com.sendkite.teatapp.user.controller.response.UserResponse;
import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserStatus;
import com.sendkite.teatapp.user.domain.UserUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserControllerTest {

    @Test
    void 사용자는_특정_유저의_정보를_개인정보는_소거된채_전달_받을_수_있다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.create();
        testContainer.userRepository.save(User.builder()
            .id(1L)
            .email("hello2@hello.com")
            .nickname("hello2")
            .address("Seoul")
            .status(UserStatus.ACTIVE)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .build()
        );

        // when
        ResponseEntity<UserResponse> result = UserController.builder()
            .userReadService(testContainer.userReadService)
            .build()
            .getUserById(1);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("hello2@hello.com");
        assertThat(result.getBody().getNickname()).isEqualTo("hello2");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_존재하지_않는_유저의_아이디로_api_호출할_경우_404_응답을_받는다() throws Exception {
        TestContainer testContainer = TestContainer.create();
        // when
        assertThatThrownBy(() -> testContainer.userController.getUserById(1))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_인증_코드로_계정을_활성화_시킬_수_있다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.create();
        testContainer.userRepository.save(User.builder()
                .id(1L)
                .email("hello2@hello.com")
                .nickname("hello2")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .lastLoginAt(100L)
                .build());
        // when
        ResponseEntity<Void> result = testContainer.userController.verifyEmail(1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        assertThat(testContainer.userRepository.getById(1L).getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_인증_코드가_일치하지_않을_경우_권한_없음_에러를_내려준다() throws Exception {
        TestContainer testContainer = TestContainer.create();
        testContainer.userRepository.save(User.builder()
            .id(1L)
            .email("hello2@hello.com")
            .nickname("hello2")
            .address("Seoul")
            .status(UserStatus.PENDING)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .lastLoginAt(100L)
            .build());
        // when
        // then
        assertThatThrownBy(
            () -> testContainer.userController.verifyEmail(1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac"))
            .isInstanceOf(CertificationCodeNotMatchedException.class);
    }

    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_갖고_올_수_있다() {
        // given
        TestContainer testContainer = TestContainer.create();
        testContainer.userRepository.save(User.builder()
            .id(1L)
            .email("hello2@hello.com")
            .nickname("hello2")
            .address("Seoul")
            .status(UserStatus.ACTIVE)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .lastLoginAt(100L)
            .build());
        // when
        ResponseEntity<MyProfileResponse> result = testContainer.userController.getMyInfo("hello2@hello.com");

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("hello2@hello.com");
        assertThat(result.getBody().getNickname()).isEqualTo("hello2");
        assertThat(result.getBody().getLastLoginAt()).isEqualTo(123123L);
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getBody().getAddress()).isEqualTo("Seoul");
    }

    @Test
    void 사용자는_내_정보를_수정할_수_있다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.create();
        testContainer.userRepository.save(User.builder()
            .id(1L)
            .email("hello2@hello.com")
            .nickname("hello2")
            .address("Seoul")
            .status(UserStatus.ACTIVE)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .lastLoginAt(100L)
            .build());
        // when
        ResponseEntity<MyProfileResponse> result = testContainer.userController.updateMyInfo("hello2@hello.com", UserUpdate.builder()
            .nickname("hello3")
            .address("Busan")
            .build());

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmail()).isEqualTo("hello2@hello.com");
        assertThat(result.getBody().getNickname()).isEqualTo("hello3");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(result.getBody().getAddress()).isEqualTo("Busan");
    }
}
