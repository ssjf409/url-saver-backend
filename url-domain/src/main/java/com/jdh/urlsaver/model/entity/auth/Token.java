package com.jdh.urlsaver.model.entity.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Date;

@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    private static final String AUTHORITIES_KEY = "role";
    private static final String TOKEN_TYPE = "type";
    private Long tokenId;
    private String subject;
    private String type;
    private String role;
    private String token;
    private Date expiredTime;

    public static Token of(TokenEntity entity) {
        return Token.builder()
                    .tokenId(entity.getTokenId())
                    .subject(entity.getSubject())
                    .type(entity.getType())
                    .role(entity.getRole())
                    .token(entity.getToken())
                    .expiredTime(entity.getExpiredTime())
                    .build();
    }

    public static Token create(String subject, String role, String type, Key key, Date now, Date expiredTime) {
        return Token.builder()
                    .subject(subject)
                    .type(type)
                    .role(role)
                    .token(createJwtToken(subject, role, type, key, now, expiredTime))
                    .expiredTime(expiredTime)
                    .build();
    }

    private static String createJwtToken(String subject, String role, String type, Key key, Date now, Date expiry) {
        return Jwts.builder()
                   .setSubject(subject)
                   .claim(AUTHORITIES_KEY, role)
                   .claim(TOKEN_TYPE, type)
                   .signWith(key, SignatureAlgorithm.HS256)
                   .setIssuedAt(now)
                   .setExpiration(expiry)
                   .compact();
    }

    public TokenEntity toEntity() {
        return TokenEntity.builder()
                          .subject(this.subject)
                          .role(this.role)
                          .type(this.type)
                          .token(this.token)
                          .expiredTime(this.expiredTime)
                          .build();
    }
}
