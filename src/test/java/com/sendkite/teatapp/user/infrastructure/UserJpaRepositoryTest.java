package com.sendkite.teatapp.user.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import com.sendkite.teatapp.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest(showSql = true)
@Sql("/sql/user-repository-test-data.sql")
class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void findByIdAndStatus_조회_성공() {

        // given
        long id = 1L;
        UserStatus userStatus = UserStatus.ACTIVE;

        // when
        var user = userJpaRepository.findByIdAndStatus(id, userStatus);

        // then
        assertThat(user).isPresent();
        assertThat(user.get().getId()).isEqualTo(id);
        assertThat(user.get().getStatus()).isEqualTo(userStatus);
    }

    @Test
    void findByIdAndStatus_조회_실패_Optional_empty() {

        // given
        long id = 2L;
        UserStatus userStatus = UserStatus.ACTIVE;

        // when
        var user = userJpaRepository.findByIdAndStatus(id, userStatus);

        // then
        assertThat(user.isEmpty()).isTrue();
    }

    @Test
    void findByEmailAndStatus_성공() {

        // given
        String email = "hello@hello.com";

        // when
        var user = userJpaRepository.findByEmailAndStatus(email, UserStatus.ACTIVE);

        // then
        assertThat(user.isPresent()).isTrue();
    }

    @Test
    void findByEmailAndStatus_실패_Optional_empty() {

        // given
        String email = "no@hello.com";

        // when
        var user = userJpaRepository.findByEmailAndStatus(email, UserStatus.ACTIVE);

        // then
        assertThat(user.isEmpty()).isTrue();
    }
}