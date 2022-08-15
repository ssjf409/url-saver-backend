package com.jdh.urlsaver.api.application;

import com.jdh.urlsaver.api.service.EmailService;
import com.jdh.urlsaver.api.service.dto.MailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NoticeApplication {

    private static final String MAIL_TITLE = "[Email verification]";
    private static final String BASE_MESSAGE =
            "This is an email verification test. Please click the link below to verify.\n";
    private final EmailService emailService;

    public void sendVerifyEMail(String email, String targetUserId, String emailCode) {
        emailService.sendEmail(MailDto.builder()
                                      .address(email)
                                      .title(MAIL_TITLE)
                                      .message(buildMessage(targetUserId, emailCode))
                                      .build());
    }

    private String buildMessage(String targetUserId, String emailCode) {
        return BASE_MESSAGE + buildVerifyingLink(targetUserId, emailCode);
    }

    private String buildVerifyingLink(String userId, String emailCode) {
        return "http://localhost:8080/api/v1/auth/verify/email?userId=" + userId + "&code=" + emailCode;
    }
}
