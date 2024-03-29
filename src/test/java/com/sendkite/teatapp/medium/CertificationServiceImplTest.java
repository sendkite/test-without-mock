package com.sendkite.teatapp.medium;

import static org.assertj.core.api.Assertions.assertThat;

import com.sendkite.teatapp.mock.FakeMailSender;
import com.sendkite.teatapp.user.service.CertificationService;
import org.junit.jupiter.api.Test;

class CertificationServiceImplTest {

    @Test
    void 이메일_컨텐츠_제대로_만들어져서_보내지는지_테스트() {
        // given
        FakeMailSender fakeMailSender = new FakeMailSender();
        CertificationService certificationServiceImpl = new CertificationService(
            fakeMailSender);
        // when
        certificationServiceImpl.send("hello2@naver.com", 1,
            "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
        // then
        assertThat(fakeMailSender.email).isEqualTo("hello2@naver.com");
        assertThat(fakeMailSender.title).isEqualTo("TeaTapp Email Certification");
        assertThat(fakeMailSender.content).isEqualTo(
            "Please click the link below to verify your email address:\n"
                + "http://localhost:8080/api/users/1/verify?certificationCode=aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
    }
}