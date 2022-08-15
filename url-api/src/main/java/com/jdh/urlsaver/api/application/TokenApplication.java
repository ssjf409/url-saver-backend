package com.jdh.urlsaver.api.application;

import com.jdh.urlsaver.api.application.vo.AuthVo;
import com.jdh.urlsaver.api.service.TokenService;
import com.jdh.urlsaver.api.service.dto.AuthResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Component
public final class TokenApplication {

    private static final String ACCESS_TOKEN_TYPE = "access-token";
    private static final String REFRESH_TOKEN_TYPE = "refresh-token";
    private static final String HEADER = "userId";
    private final TokenService tokenService;

    public AuthVo createToken(@NotNull String userId) {
        AuthResult authResult = tokenService.createAuthToken(ACCESS_TOKEN_TYPE, REFRESH_TOKEN_TYPE, HEADER, userId);
        return AuthVo.builder()
                     .accessToken(authResult.getAccessToken().getValue())
                     .refreshToken(authResult.getRefreshToken().getValue())
                     .build();
    }
}
