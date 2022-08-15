package com.jdh.urlsaver.api.application;

import com.jdh.urlsaver.api.application.dto.LoginRequestDto;
import com.jdh.urlsaver.api.application.dto.SignUpRequestDto;
import com.jdh.urlsaver.api.application.vo.AuthVo;
import com.jdh.urlsaver.api.service.AuthService;
import com.jdh.urlsaver.api.service.dto.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Component
public final class AuthApplication {

    private final AuthService authService;
    private final TokenApplication tokenApplication;
    private final HistoryApplication historyApplication;
    private final NoticeApplication noticeApplication;

    public AuthVo signIn(@NotNull LoginRequestDto loginRequestDto) {
        // 1. check user
        User user = authService.signIn(loginRequestDto.getLoginId(), loginRequestDto.getPassword());

        // 2. issue token
        AuthVo auth = tokenApplication.createToken(String.valueOf(user.getUserId()));

        // 3. after login
        // delete device id other
//        loginRequestDto.getDeviceId();
//        historyApplication.signIn(user);


        return auth;

    }

    public User signUp(SignUpRequestDto signUpRequestDto) {
        User user = authService.register(signUpRequestDto);

        // after sign up
        String emailCode = RandomStringUtils.randomAlphanumeric(20);
        historyApplication.leaveSignUpEvent(user, emailCode);
        noticeApplication.sendVerifyEMail(user.getEmail(), String.valueOf(user.getUserId()), emailCode);
        return user;
    }

    public void verifyEmail(Long userId, String code) {
        historyApplication.validateEmail(String.valueOf(userId), code);
        // after success validation
        authService.successEmailVerification(userId);
    }
}
