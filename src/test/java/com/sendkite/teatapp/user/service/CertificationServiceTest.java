package com.sendkite.teatapp.user.service;

import static org.assertj.core.api.Assertions.*;

import com.sendkite.teatapp.mock.FakeMailSender;
import org.junit.jupiter.api.Test;

class CertificationServiceTest {

    @Test
    void 이메일_컨텐츠_제대로_만들어져서_보내지는지_테스트() {
        // given
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationService = new CertificationService(fakeMailSender);
        // when
        certificationService.send("hello2@naver.com", 1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
        // then
        assertThat(fakeMailSender.email).isEqualTo("hello2@naver.com");
        assertThat(fakeMailSender.title).isEqualTo("TeaTapp Email Certification");
        assertThat(fakeMailSender.content).isEqualTo("Please click the link below to verify your email address:\n"
            + "http://localhost:8080/api/users/1/verify?certificationCode=aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
    }
}