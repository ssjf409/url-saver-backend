package com.jdh.urlsaver.api.service;

import com.jdh.urlsaver.api.service.dto.MailDto;
import com.jdh.urlsaver.domain.common.exception.EmailSendException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class EmailService {
    private static final String FROM_ADDRESS = "ssjf409@gmail.com";
    private final JavaMailSender emailSender;

    public void sendEmail(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        try {
            emailSender.send(message);
        } catch (Exception e) {
            throw new EmailSendException(String.format(
                    "failed to send email, email: %s, message: %s", mailDto.getAddress(), e.getMessage()));
        }
    }
}
