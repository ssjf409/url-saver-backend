package com.jdh.urlsaver.api.application;

import com.jdh.urlsaver.api.application.dto.LoginRequest;
import com.jdh.urlsaver.api.application.dto.LoginResponse;
import com.jdh.urlsaver.api.application.dto.SignUpRequest;
import com.jdh.urlsaver.api.service.AccountService;
import com.jdh.urlsaver.api.service.dto.AuthResult;
import com.jdh.urlsaver.api.service.dto.User;
import com.jdh.urlsaver.configuration.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Component
public final class AuthApplication {

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final TokenApplication tokenApplication;
    private final HistoryApplication historyApplication;
    private final NoticeApplication noticeApplication;

    public LoginResponse signIn(@NotNull LoginRequest loginRequest) {
        // 1. check user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLoginId(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String role = ((UserPrincipal) authentication.getPrincipal()).getRoleType().getCode();

        // 2. issue token
        AuthResult auth = tokenApplication.createToken(String.valueOf(principal.getUserId()), role);

        // 3. after login
        // delete device id other
        // TODO: 2022/08/21
//        loginRequestDto.getDeviceId();
//        historyApplication.signIn(user);

        return LoginResponse.builder()
                            .accessToken(auth.getAccessToken().getToken())
                            .refreshToken(auth.getRefreshToken().getToken())
                            .build();

    }

    public User signUp(SignUpRequest signUpRequest) {
        User user = accountService.register(signUpRequest);

        // after sign up
        String emailCode = RandomStringUtils.randomAlphanumeric(20);
        historyApplication.leaveSignUpEvent(user, emailCode);
        noticeApplication.sendVerifyEMail(user.getEmail(), String.valueOf(user.getUserId()), emailCode);
        return user;
    }

    public void verifyEmail(Long userId, String code) {
        historyApplication.validateEmail(String.valueOf(userId), code);
        // after success validation
        accountService.successEmailVerification(userId);
    }
}
