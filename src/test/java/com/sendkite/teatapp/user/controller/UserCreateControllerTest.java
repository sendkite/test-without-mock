package com.sendkite.teatapp.user.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.sendkite.teatapp.mock.TestContainer;
import com.sendkite.teatapp.user.controller.response.MyProfileResponse;
import com.sendkite.teatapp.user.controller.response.UserResponse;
import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserCreate;
import com.sendkite.teatapp.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserCreateControllerTest {

    @Test
    void 회원가입_성공_상태는_PENDING() {
        // given
        TestContainer testContainer = TestContainer.create();
        // when
        ResponseEntity<UserResponse> result = testContainer.userCreateController.createUser(
            UserCreate.builder()
                .email("create-user@gmail.com")
                .nickname("hello-n")
                .address("Seoul")
                .build());

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result.getBody().getEmail()).isEqualTo("create-user@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("hello-n");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
    }
}
