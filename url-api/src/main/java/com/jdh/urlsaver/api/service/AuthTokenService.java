package com.jdh.urlsaver.api.service;

import com.jdh.urlsaver.api.application.dto.LoginResultDto;
import com.jdh.urlsaver.api.entity.user.Token;
import com.jdh.urlsaver.api.repository.user.UserRefreshTokenRepository;
import com.jdh.urlsaver.api.service.dto.RefreshTokenDto;
import com.jdh.urlsaver.config.properties.AppProperties;
import com.jdh.urlsaver.model.entity.account.RoleType;
import com.jdh.urlsaver.oauth.token.AuthToken;
import com.jdh.urlsaver.oauth.token.AuthTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthTokenService {

    private final static long THREE_DAYS_MILLI_SEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";
    private final AppProperties appProperties;
    private final AuthTokenProvider tokenProvider;
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    @Transactional
    public LoginResultDto createOrUpdate(String userId, RoleType roleType) {

        Date now = new Date();

        AuthToken accessToken = tokenProvider.createAuthToken(
                userId,
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long accessTokenExpiry = appProperties.getAuth().getTokenExpiry();
        long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                appProperties.getAuth().getTokenSecret(),
                new Date(now.getTime() + refreshTokenExpiry)
        );

        // userId refresh token 으로 DB 확인
        Token token = userRefreshTokenRepository.findByUserId(userId);
        if (token == null) {
            // 없는 경우 새로 등록
            token = new Token(userId, refreshToken.getToken());
            userRefreshTokenRepository.saveAndFlush(token);
        } else {
            // DB에 refresh 토큰 업데이트
            token.setRefreshToken(refreshToken.getToken());
        }

        return LoginResultDto.builder()
                             .accessToken(accessToken.getToken())
                             .accessTokenExpiry(accessTokenExpiry)
                             .refreshToken(refreshToken.getToken())
                             .refreshTokenExpiry(refreshTokenExpiry)
                             .build();
    }

    public AuthToken convertToRefreshToken(String refreshToken) {
        // refresh token
        AuthToken authRefreshToken = tokenProvider.convertAuthToken(refreshToken);

        if (!authRefreshToken.validate()) {
//            return ApiResponse.invalidRefreshToken();
            throw new RuntimeException("hello");
        }
        return authRefreshToken;
    }

    @Transactional(readOnly = true)
    public Token findTokenByUserIdAndRefreshToken(String userId, String refreshToken) {
        return Optional.ofNullable(userRefreshTokenRepository.findByUserIdAndRefreshToken(userId, refreshToken))
                       .orElseThrow(() -> new RuntimeException("hello"));
    }

    @Transactional
    public void setRefreshToken(Token token, String refreshToken) {
        token.setRefreshToken(refreshToken);
        userRefreshTokenRepository.save(token);
    }

    public RefreshTokenDto refresh(AuthToken accessAuthToken, String refreshToken) {

        // expired access token 인지 확인
        Claims claims = accessAuthToken.getExpiredTokenClaims();
        if (claims == null) {
//            return ApiResponse.notExpiredTokenYet();
            throw new RuntimeException("hello");
        }

        String userId = claims.getSubject();
        RoleType roleType = RoleType.of(claims.get("role", String.class));
        Token token = findTokenByUserIdAndRefreshToken(userId, refreshToken);


        Date now = new Date();
        AuthToken newAccessToken = tokenProvider.createAuthToken(
                userId,
                roleType.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        AuthToken authRefreshToken = convertToRefreshToken(refreshToken);

        long validTime = authRefreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();
        boolean isNewRefreshToken = false;

        // refresh 토큰 기간이 3일 이하로 남은 경우, refresh 토큰 갱신
        if (validTime <= THREE_DAYS_MILLI_SEC) {
            // refresh 토큰 설정
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            authRefreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );


            setRefreshToken(token, authRefreshToken.getToken());
            // DB에 refresh 토큰 업데이트
//            token.setRefreshToken(authRefreshToken.getToken());

            isNewRefreshToken = true;
        }

        return RefreshTokenDto.builder()
                              .accessToken(newAccessToken)
                              .refreshToken(authRefreshToken)
                              .isNewRefreshToken(isNewRefreshToken)
                              .build();
    }
}
