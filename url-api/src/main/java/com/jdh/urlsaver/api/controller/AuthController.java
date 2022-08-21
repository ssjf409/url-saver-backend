package com.jdh.urlsaver.api.controller;

import com.jdh.urlsaver.api.application.AuthApplication;
import com.jdh.urlsaver.api.controller.dto.LoginRequest;
import com.jdh.urlsaver.api.controller.dto.LoginResponse;
import com.jdh.urlsaver.api.controller.dto.SignUpRequest;
import com.jdh.urlsaver.configuration.properties.AppProperties;
import com.jdh.urlsaver.model.entity.auth.TokenType;
import com.jdh.urlsaver.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.jdh.urlsaver.model.entity.auth.TokenType.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    private final AppProperties appProperties;
    private final AuthApplication authApplication;

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponse> login(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = authApplication.signIn(loginRequest);
        setNewCookies(request, response, loginResponse);
        return ResponseEntity.ok(loginResponse);
    }

    private void setNewCookies(HttpServletRequest request, HttpServletResponse response, LoginResponse loginResponse) {
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN.getHeader());
        CookieUtil.addCookie(response, ACCESS_TOKEN.getHeader(), loginResponse.getAccessToken(),
                             appProperties.getAuth().getTokenExpirySec());
        CookieUtil.deleteCookie(request, response, TokenType.REFRESH_TOKEN.getHeader());
        CookieUtil.addCookie(response, TokenType.REFRESH_TOKEN.getHeader(), loginResponse.getRefreshToken(),
                             appProperties.getAuth().getRefreshTokenExpirySec());
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        authApplication.signUp(signUpRequest);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/verify/email")
    public ResponseEntity<Boolean> verifyEmail(@RequestParam Long userId, @RequestParam String code) {
        authApplication.verifyEmail(userId, code);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
