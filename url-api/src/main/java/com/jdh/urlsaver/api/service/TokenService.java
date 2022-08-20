package com.jdh.urlsaver.api.service;

import com.jdh.urlsaver.api.application.AuthApplication;
import com.jdh.urlsaver.api.repository.TokenRepository;
import com.jdh.urlsaver.api.service.dto.AuthResult;
import com.jdh.urlsaver.common.exception.TokenValidFailedException;
import com.jdh.urlsaver.configuration.properties.AppProperties;
import com.jdh.urlsaver.model.entity.auth.Token;
import com.jdh.urlsaver.model.entity.auth.TokenEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TokenService {

    private static final String ACCESS_TOKEN_TYPE = "access-token";
    private static final String REFRESH_TOKEN_TYPE = "refresh-token";

    private final Key key;
    private final TokenRepository tokenRepository;
    private final AppProperties appProperties;


    public TokenService(@Value("${jwt.secret}") String secret,
                        TokenRepository tokenRepository,
                        AppProperties appProperties
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.tokenRepository = tokenRepository;
        this.appProperties = appProperties;
    }

    @Transactional
    public AuthResult createAuthToken(String subject, String role) {
        Date now = new Date();
        Date accessTokenExpiry = new Date(now.getTime() + appProperties.getAuth().getTokenExpiry());
        Date refreshTokenExpiry = new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry());
        Token accessToken = Token.create(subject, role, ACCESS_TOKEN_TYPE, key, now, accessTokenExpiry);
        Token refreshToken = Token.create(subject, role, REFRESH_TOKEN_TYPE, key, now, refreshTokenExpiry);

        TokenEntity accessTokenEntity = accessToken.toEntity();
        TokenEntity refreshTokenEntity = tokenRepository.findBySubjectAndType(subject, REFRESH_TOKEN_TYPE);
        if (refreshTokenEntity == null) {
            refreshTokenEntity = refreshToken.toEntity();
        } else {
            // DB에 refresh 토큰 업데이트
            refreshTokenEntity.setToken(refreshToken.getToken());
            refreshTokenEntity.setRole(role);
        }
        Token savedAccessToken = Token.of(tokenRepository.save(accessTokenEntity));
        Token savedRefreshToken = Token.of(tokenRepository.save(refreshTokenEntity));

        return AuthResult.builder()
                         .accessToken(savedAccessToken)
                         .refreshToken(savedRefreshToken)
                         .build();
    }


    public boolean validate(String tokenStr) {
        return getTokenClaims(tokenStr) != null;
    }

//    public Authentication validate(String tokenStr) {
//        if (getTokenClaims(tokenStr)) {
//            return
//        }
//        return ;
//
//        Token token = Token.builder()
//                           .token(tokenStr)
//                           .build();
//        Claims claims = getTokenClaims(token.getToken());
//
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(new String[] { claims.get("role").toString() })
//                      .map(SimpleGrantedAuthority::new)
//                      .collect(Collectors.toList());
//
//        log.debug("claims subject := [{}]", claims.getSubject());
//        User principal = new User(claims.getSubject(), "", authorities);
//
//        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
//    }

//    public Authentication validate(String tokenStr) {
////        return getTokenClaims(tokenStr);
//
//        Token token = Token.builder()
//                           .token(tokenStr)
//                           .build();
//        Claims claims = getTokenClaims(token.getToken());
//
//        Collection<? extends GrantedAuthority> authorities =
//                Arrays.stream(new String[] { claims.get("role").toString() })
//                      .map(SimpleGrantedAuthority::new)
//                      .collect(Collectors.toList());
//
//        log.debug("claims subject := [{}]", claims.getSubject());
//        User principal = new User(claims.getSubject(), "", authorities);
//
//        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
//    }

    public Authentication getAuthentication(String tokenStr) {
        if (validate(tokenStr)) {
            Claims claims = getTokenClaims(tokenStr);
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(new String[] { claims.get("role").toString() })
                          .map(SimpleGrantedAuthority::new)
                          .collect(Collectors.toList());
            log.debug("claims subject := [{}]", claims.getSubject());
            User principal = new User(claims.getSubject(), "", authorities);
            return new UsernamePasswordAuthenticationToken(principal, Token.builder().token(tokenStr).build(),
                                                           authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }


    private Claims getTokenClaims(String token) {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(key)
                       .build()
                       .parseClaimsJws(token)
                       .getBody();
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }


    public Claims getExpiredTokenClaims(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            return e.getClaims();
        }
        return null;
    }

    public AuthApplication refresh(String tokenStr) {
        return null;
    }


    //    @Transactional
//    public AuthResult initAuthToken(Token accessToken, Token refreshToken) {
//        Token savedAccessToken = saveToken(accessToken);
//        Token savedRefreshToken = saveToken(refreshToken);
//        return AuthResult.builder()
//                         .accessToken(savedAccessToken)
//                         .refreshToken(savedRefreshToken)
//                         .build();
//    }
//
//    @Transactional
//    public Token saveToken(Token token) {
//        AuthTokenEntity existing = tokenRepository.findByTypeAndHeaderAndHeaderValue(token.getType(),
//                                                                                     token.getHeader(),
//                                                                                     token.getHeaderValue());
//        AuthTokenEntity authTokenEntity = Optional.ofNullable(existing)
//                                                  .orElseGet(() -> AuthTokenEntity.builder().build());
//        AuthTokenEntity newToken = token.toEntity();
//        newToken.setTokenId(authTokenEntity.getTokenId());
//        return TokenConverter.convert(tokenRepository.save(newToken));
//    }
//
//    public String validate(String tokenString) {
//        Token token = new Token(this.secret, tokenString);
//        Claims tokenClaims = token.getTokenClaims();
//        if (tokenClaims == null) {
//            throw new TokenValidFailedException(String.format("failed to validate token, token: %s", tokenString));
//        }
//
//        return tokenClaims.get("userId", String.class);
//    }

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
