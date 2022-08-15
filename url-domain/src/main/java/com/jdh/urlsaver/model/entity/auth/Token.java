package com.jdh.urlsaver.model.entity.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Getter
@Setter
//@Builder
//@AllArgsConstructor
//@RequiredArgsConstructor
public class Token {
    private String secret;
    private Long tokenId;
    private String type; // service name
    private String header; // X-ESt
    private String headerValue;
    private String value;
    private Date expiredTime;

//    public AuthToken(String id, String role, Date expiry, Key key) {
//        this.key = key;
//        this.token = createAuthToken(id, role, expiry);
//    }

    public Token(String secret, String value) {
        this.secret = secret;
        this.value = value;
    }

    public Token(String secret, String type, String header,
                 String headerValue, Date expiredTime) {
        this.secret = secret;
        this.type = type;
        this.header = header;
        this.headerValue = headerValue;
        this.expiredTime = expiredTime;
        this.value = createAuthToken(secret, type, header, headerValue, expiredTime);
    }

//    private String createAuthToken(String id, Date expiredTime) {
//        return Jwts.builder()
//                   .setSubject(id)
//                   .signWith(Keys.hmacShaKeyFor(key.getBytes()), SignatureAlgorithm.HS256)
//                   .setExpiration(expiredTime)
//                   .compact();
//    }

    private String createAuthToken(String key, String type, String header, String headerValue, Date expiredTime) {
        return Jwts.builder()
                   .setSubject(type)
                   .setHeaderParam(header, headerValue)
                   .signWith(Keys.hmacShaKeyFor(key.getBytes()), SignatureAlgorithm.HS256)
                   .setExpiration(expiredTime)
                   .compact();
    }

    public boolean validate() {
        return this.getTokenClaims() != null;
    }

    public Claims getTokenClaims() {
        try {
            return Jwts.parserBuilder()
                       .setSigningKey(Keys.hmacShaKeyFor(this.secret.getBytes()))
                       .build()
                       .parseClaimsJws(this.value)
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

    public Claims getExpiredTokenClaims() {
        try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(this.secret.getBytes()))
                .build()
                .parseClaimsJws(this.value)
                .getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            return e.getClaims();
        }
        return null;
    }

    public TokenEntity toEntity() {
        return TokenEntity.builder()
                          .secret(this.getSecret())
                          .type(this.getType())
                          .header(this.getHeader())
                          .headerValue(this.getHeaderValue())
                          .value(this.getValue())
                          .expiredTime(this.getExpiredTime())
                          .build();
    }
}
