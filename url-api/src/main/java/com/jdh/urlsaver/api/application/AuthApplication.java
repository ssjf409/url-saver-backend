package com.jdh.urlsaver.api.application;

import com.jdh.urlsaver.api.application.dto.LoginRequestDto;
import com.jdh.urlsaver.api.controller.vo.AuthVo;
import com.jdh.urlsaver.api.service.AuthService;
import com.jdh.urlsaver.api.service.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Component
public final class AuthApplication {

    private final AuthService authService;
    private final TokenApplication tokenApplication;

    public AuthVo login(@NotNull LoginRequestDto loginRequestDto) {
        // 1. check user
        User user = authService.login(loginRequestDto.getLoginId(), loginRequestDto.getPassword());

        // 2. issue token
        AuthVo auth = tokenApplication.createToken(String.valueOf(user.getUserId()));

        // 3. after login
        // delete device id other
//        loginRequestDto.getDeviceId();

        return auth;

    }
}
