package com.sendkite.teatapp.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.sendkite.teatapp.common.domain.exception.CertificationCodeNotMatchedException;
import com.sendkite.teatapp.common.domain.exception.ResourceNotFoundException;
import com.sendkite.teatapp.mock.FakeMailSender;
import com.sendkite.teatapp.mock.FakeUserRepository;
import com.sendkite.teatapp.mock.TestClockHolder;
import com.sendkite.teatapp.mock.TestUuidHolder;
import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserCreate;
import com.sendkite.teatapp.user.domain.UserStatus;
import com.sendkite.teatapp.user.domain.UserUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserServiceImplTest {

    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void init() {
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.userServiceImpl = UserServiceImpl.builder()
            .userRepository(fakeUserRepository)
            .certificationService(new CertificationService(fakeMailSender))
            .uuidHolder(new TestUuidHolder("aaaa-aaaaa-aaaaa-aaaaaab"))
            .clockHolder(new TestClockHolder(1663434343L))
            .build();

        fakeUserRepository.save(User.builder()
            .id(1L)
            .nickname("admin")
            .email("hello@hello.com")
            .address("Seoul")
            .certificationCode("aaaa-aaaaa-aaaaa-aaaaaaa")
            .status(UserStatus.ACTIVE)
            .lastLoginAt(0L)
            .build());

        fakeUserRepository.save(User.builder()
            .id(2L)
            .nickname("test")
            .email("hello2@hello.com")
            .address("Seoul")
            .certificationCode("aaaa-aaaaa-aaaaa-aaaaaab")
            .status(UserStatus.PENDING)
            .lastLoginAt(0L)
            .build());
    }

    @Test
    void getByEmail은_ACTIVE_유저_반환() {
        // given
        String email = "hello@hello.com";

        // when
        var user = userServiceImpl.getByEmail(email);

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
            var user = userServiceImpl.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById는_ACTIVE_유저_반환() {
        // given
        // when
        var user = userServiceImpl.getById(1L);

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
            var user = userServiceImpl.getById(2L);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userCreateDto_를_이용하여_유저를_생성할_수_있다() {
        // given
        UserCreate userCreate = UserCreate.builder()
            .email("test@hello.com")
            .address("Gyeongi")
            .nickname("hello-create")
            .build();

        // when
        User user = userServiceImpl.create(userCreate);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaa-aaaaa-aaaaa-aaaaaab");
    }

    @Test
    void userUpdateDto_를_이용하여_유저를_수정할_수_있다() {
        // given
        UserUpdate userUpdate = UserUpdate.builder()
            .address("Incheon")
            .nickname("songyeon")
            .build();

        // when
        userServiceImpl.update(1L, userUpdate);

        // then
        User user = userServiceImpl.getById(1L);
        assertThat(user.getNickname()).isEqualTo("songyeon");
        assertThat(user.getAddress()).isEqualTo("Incheon");
    }

    @Test
    void 로그인하면_로그인_시간_기록() {
        // given
        // when
        userServiceImpl.login(1);

        // then
        User user = userServiceImpl.getById(1L);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getLastLoginAt()).isEqualTo(1663434343L);
    }

    @Test
    void PENDING_USER_인증_코드로_ACTIVE() {
        // given
        // when
        userServiceImpl.verifyEmail(2, "aaaa-aaaaa-aaaaa-aaaaaab");

        // then
        User user = userServiceImpl.getById(2);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING_USER_인증_실패() {
        // given
        // when
        // then
        assertThatThrownBy(() ->
            userServiceImpl.verifyEmail(2, "aaaa-worng-code")
        ).isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}