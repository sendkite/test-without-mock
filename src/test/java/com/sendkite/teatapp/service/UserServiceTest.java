package com.sendkite.teatapp.service;

import com.sendkite.teatapp.exception.ResourceNotFoundException;
import com.sendkite.teatapp.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@Sql("/sql/user-service-test-data.sql")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getByEmail은_ACTIVE_유저_반환() {
        // given
        String email = "hello@hello.com";

        // when
        var user = userService.getByEmail(email);

        // then
        assertThat(user.getNickname()).isEqualTo("admin");
        assertThat(user.getEmail()).isEqualTo("hello@hello.com");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void getByEmail은_PENDINMG_유저_조회_안됨() {
        // given
        String email = "hello2@hello.com";

        // when
        // then
        assertThatThrownBy(() -> {
                var user = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById는_ACTIVE_유저_반환() {
        // given
        // when
        var user = userService.getById(1L);

        // then
        assertThat(user.getNickname()).isEqualTo("admin");
        assertThat(user.getEmail()).isEqualTo("hello@hello.com");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void getById는_PENDINMG_유저_조회_안됨() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            var user = userService.getById(2L);
        }).isInstanceOf(ResourceNotFoundException.class);
    }
}