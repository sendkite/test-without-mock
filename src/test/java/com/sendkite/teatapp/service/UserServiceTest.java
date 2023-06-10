package com.sendkite.teatapp.service;

import com.sendkite.teatapp.exception.CertificationCodeNotMatchedException;
import com.sendkite.teatapp.exception.ResourceNotFoundException;
import com.sendkite.teatapp.model.UserStatus;
import com.sendkite.teatapp.model.dto.UserCreateDto;
import com.sendkite.teatapp.model.dto.UserUpdateDto;
import com.sendkite.teatapp.repository.UserEntity;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
})
@Sql("/sql/user-service-test-data.sql")
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private JavaMailSender mailSender;

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

    @Test
    void userCreateDto_를_이용하여_유저를_생성할_수_있다() {
        // given
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .email("test@hello.com")
                .address("Gyeongi")
                .nickname("hello-create")
                .build();
        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // when
        UserEntity userEntity = userService.create(userCreateDto);

        assertThat(userEntity.getId()).isNotNull();
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.PENDING);
//        assertThat(userEntity.getCertificationCode()).isEqualTo("123456"); // FIXME
    }

    @Test
    void userUpdateDto_를_이용하여_유저를_수정할_수_있다() {
        // given
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .address("Incheon")
                .nickname("songyeon")
                .build();

        // when
        userService.update(1L, userUpdateDto);

        // then
        UserEntity user = userService.getById(1L);
        assertThat(user.getNickname()).isEqualTo("songyeon");
        assertThat(user.getAddress()).isEqualTo("Incheon");
    }

    @Test
    void 로그인하면_로그인_시간_기록() {
        // given
        // when
        userService.login(1);

        // then
        UserEntity user = userService.getById(1L);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getLastLoginAt()).isGreaterThan(0L); // FIXME
    }

    @Test
    void PENDING_USER_인증_코드로_ACTIVE() {
        // given
        // when
        userService.verifyEmail(2, "aaaa-aaaaa-aaaaa-aaaaaab");

        // then
        UserEntity user = userService.getById(2);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING_USER_인증_실패() {
        // given
        // when
        // then
        assertThatThrownBy(() ->
                userService.verifyEmail(2, "aaaa-worng-code")
        ).isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}