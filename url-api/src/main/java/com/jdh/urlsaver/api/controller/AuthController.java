package com.jdh.urlsaver.api.controller;

import com.jdh.urlsaver.api.application.AuthApplication;
import com.jdh.urlsaver.api.application.dto.LoginRequestDto;
import com.jdh.urlsaver.api.controller.vo.AuthVo;
import com.jdh.urlsaver.configuration.properties.AppProperties;
import com.jdh.urlsaver.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.jdh.urlsaver.model.entity.auth.TokenType.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AuthApplication authApplication;
    private final AppProperties appProperties;

    // TODO: 2022/08/06 add validator
    @PostMapping("/login")
    public ResponseEntity<AuthVo> login(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody LoginRequestDto loginRequestDto) {
        // TODO: 2022/08/14 delete login logging
        log.info("[Login]");
        AuthVo auth = authApplication.login(loginRequestDto);
        setNewCookies(request, response, auth);
        return ResponseEntity.ok(auth);
    }

    private void setNewCookies(HttpServletRequest request, HttpServletResponse response, AuthVo auth) {
        long accessTokenExpiry = appProperties.getAuth().getTokenExpiry();
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN.getHeader());
        CookieUtil.addCookie(response, ACCESS_TOKEN.getHeader(), auth.getAccessToken(),
                             (int) accessTokenExpiry / 60);
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN.getHeader());
        CookieUtil.addCookie(response, REFRESH_TOKEN.getHeader(), auth.getRefreshToken(),
                             (int) refreshTokenExpiry / 60);
    }
}
