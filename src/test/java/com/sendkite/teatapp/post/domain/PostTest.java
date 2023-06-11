package com.sendkite.teatapp.post.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.sendkite.teatapp.mock.TestClockHolder;
import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    void PostCreate_게시물_생성() {
        // given
        PostCreate postCreate = PostCreate.builder()
            .writerId(1)
            .content("helloworld")
            .build();

        User writer = User.builder()
            .email("hello2@naver.com")
            .nickname("hello2")
            .address("Seoul")
            .status(UserStatus.ACTIVE)
            .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
            .build();

        // when
        Post post = Post.from(writer, postCreate, new TestClockHolder(1679530673958L));

        // then
        assertThat(post.getContent()).isEqualTo("helloworld");
        assertThat(post.getWriter().getEmail()).isEqualTo("hello2@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("hello2");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getCreatedAt()).isEqualTo(1679530673958L);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo(
            "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
    }
}