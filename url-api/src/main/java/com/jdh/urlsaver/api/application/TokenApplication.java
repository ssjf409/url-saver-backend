package com.jdh.urlsaver.api.application;

import com.jdh.urlsaver.api.service.TokenService;
import com.jdh.urlsaver.api.service.dto.AuthResult;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Component
public final class TokenApplication {

    private final TokenService tokenService;

    public AuthResult createToken(@NotNull String userId, String role) {
        return tokenService.createAuthToken(userId, role);
    }

    public boolean validate(String tokenStr) {
        return tokenService.validate(tokenStr);
    }

    public Authentication getAuthentication(String tokenStr) {
        return tokenService.getAuthentication(tokenStr);
    }

    public Tuple2<Authentication, AuthResult> refreshAuthentication(String tokenStr) {
        Authentication authentication = tokenService.getAuthentication(tokenStr);
        User user = (User) authentication.getPrincipal();
        AuthResult authToken =
                tokenService.createAuthToken(user.getUsername(),
                                             user.getAuthorities().stream().findFirst().get().getAuthority());
        return Tuple.of(authentication, authToken);
    }

//    public String validateAccessToken(@NotNull String accessToken) {
//        return tokenService.validate(accessToken);
//    }
}
