package com.sendkite.teatapp.user.service;

import com.sendkite.teatapp.user.service.port.MailSender;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final MailSender mailSender;

    public void send(String email, long userId, String certificationCode) {
        String certificationUrl = generateCertificationUrl(userId, certificationCode);
        var title = "TeaTapp Email Certification";
        var content = "Please click the link below to verify your email address:\n" + certificationUrl;
        mailSender.send(email, title, content);
    }

    private String generateCertificationUrl(long userId, String certificationCode) {
        return "http://localhost:8080/api/users/" + userId
            + "/verify?certificationCode=" + certificationCode;
    }
}
