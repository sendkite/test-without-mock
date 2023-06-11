package com.sendkite.teatapp.post.controller.response;

import static org.assertj.core.api.Assertions.assertThat;

import com.sendkite.teatapp.post.domain.Post;
import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

class PostResponseTest {

    @Test
    void Post로_응답_변환() {
        // given
        Post post = Post.builder()
            .id(1L)
            .content("helloworld")
            .writer(User.builder()
                .email("hello2@naver.com")
                .nickname("hello2")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build())
            .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.getContent()).isEqualTo("helloworld");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("hello2@naver.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("hello2");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}
