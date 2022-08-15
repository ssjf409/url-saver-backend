package com.jdh.urlsaver.api.service;

import com.jdh.urlsaver.api.repository.TokenRepository;
import com.jdh.urlsaver.api.service.dto.AuthResult;
import com.jdh.urlsaver.common.converter.TokenConverter;
import com.jdh.urlsaver.common.exception.TokenValidFailedException;
import com.jdh.urlsaver.configuration.properties.AppProperties;
import com.jdh.urlsaver.model.entity.auth.Token;
import com.jdh.urlsaver.model.entity.auth.TokenEntity;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class TokenService {

    private static final String ACCESS_TOKEN_TYPE = "access-token";
    private static final String REFRESH_TOKEN_TYPE = "refresh-token";
    private static final String HEADER = "userId";
    private final TokenRepository tokenRepository;
    private final AppProperties appProperties;
    private final String secret;

    public TokenService(TokenRepository tokenRepository,
                        AppProperties appProperties,
                        @Value("${jwt.secret}") String secret) {
        this.tokenRepository = tokenRepository;
        this.appProperties = appProperties;
        this.secret = secret;
    }

//    public AuthToken createAuthToken(String id, Date expiry) {
//        return new AuthToken(id, expiry, key);
//    }

    public AuthResult createAuthToken(String headerValue) {
        Date now = new Date();
        Date accessibleTime = new Date(now.getTime() + appProperties.getAuth().getTokenExpiry());
        Date refreshableTime = new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry());
        Token accessToken = new Token(this.secret, ACCESS_TOKEN_TYPE, HEADER, headerValue, accessibleTime);
        Token refreshToken = new Token(this.secret, REFRESH_TOKEN_TYPE, HEADER, headerValue, refreshableTime);

        return initAuthToken(accessToken, refreshToken);
    }

    @Transactional
    public AuthResult initAuthToken(Token accessToken, Token refreshToken) {
        Token savedAccessToken = saveToken(accessToken);
        Token savedRefreshToken = saveToken(refreshToken);
        return AuthResult.builder()
                         .accessToken(savedAccessToken)
                         .refreshToken(savedRefreshToken)
                         .build();
    }

    @Transactional
    public Token saveToken(Token token) {
        TokenEntity existing = tokenRepository.findByTypeAndHeaderAndHeaderValue(token.getType(),
                                                                                 token.getHeader(),
                                                                                 token.getHeaderValue());
        TokenEntity tokenEntity = Optional.ofNullable(existing)
                                          .orElseGet(() -> TokenEntity.builder().build());
        TokenEntity newToken = token.toEntity();
        newToken.setTokenId(tokenEntity.getTokenId());
        return TokenConverter.convert(tokenRepository.save(newToken));
    }

    public String validate(String tokenString) {
        Token token = new Token(this.secret, tokenString);
        Claims tokenClaims = token.getTokenClaims();
        if (tokenClaims == null) {
            throw new TokenValidFailedException(String.format("failed to validate token, token: %s", tokenString));
        }

        return tokenClaims.get("userId", String.class);
    }

//    @Transactional
//    public Token createToken(String header, String headerValue) {
////        String key, String type, String header,
////                String headerValue, Date expiredTime
////
////        Date accessibleTime = new Date((new Date()).getTime() + appProperties.getAuth().getTokenExpiry());
////        Date refreshableTime = new Date((new Date()).getTime() + appProperties.getAuth().getRefreshTokenExpiry());
////
////        Token accessToken = new Token(this.key, ACCESS_TOKEN_TYPE, header, headerValue, accessibleTime);
////
////        Token refreshToken = new Token(this.key, REFRESH_TOKEN_TYPE, header, headerValue, refreshableTime);
//
//        TokenEntity tokenEntity = Optional.ofNullable(tokenRepository.findByTypeAndHeaderAndHeaderValue(headerValue))
//                                          .orElseGet(() -> TokenEntity.builder()
//                                                                      .value(headerValue)
//                                                                      .build());
//
//        tokenEntity.setAccessibleTime(TimeUtils.toLocalDateTime(accessibleTime));
//        tokenEntity.setRefreshableTime(TimeUtils.toLocalDateTime(refreshableTime));
//        tokenEntity.setAccessToken(accessToken);
//        tokenEntity.setRefreshToken(refreshToken);
//
//
////        if (!authTokenEntity.getDeviceId().equals(deviceId)) {
////            // TODO: 2022/08/07 other device logout
////        }
////        authTokenEntity
//        TokenEntity save = tokenRepository.save(tokenEntity);
//
////        return new Token(value, accessibleTime, key);
//        return null;
//    }

//    public AuthToken convertAuthToken(String token) {
//        return new AuthToken(token, key);
//    }




    /*
    public Authentication getAuthentication(Token token) {

        if (token.validate()) {

            Claims claims = token.getTokenClaims();
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[] { claims.get(AUTHORITIES_KEY).toString() })
                          .map(SimpleGrantedAuthority::new)
                          .collect(Collectors.toList());

            log.debug("claims subject := [{}]", claims.getSubject());
            User principal = new User(claims.getSubject(), "", authorities);

            return new UsernamePasswordAuthenticationToken(principal, token, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }

     */

}
