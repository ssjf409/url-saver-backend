package com.jdh.urlsaver.api.application;

import com.jdh.urlsaver.api.application.dto.LoginResultDto;
import com.jdh.urlsaver.api.entity.auth.LoginRequest;
import com.jdh.urlsaver.api.service.AuthTokenService;
import com.jdh.urlsaver.model.entity.account.RoleType;
import com.jdh.urlsaver.oauth.entity.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthApplication {


    private final AuthenticationManager authenticationManager;
    private final AuthTokenService authTokenService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public LoginResultDto login(LoginRequest loginRequest) {
        // [Spring security] 3
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getId(),
                        loginRequest.getPassword()
                )
        );

//        SecurityContextHolder.getContext().setAuthentication(authentication);
        String userId = loginRequest.getId();
        RoleType roleType = ((UserPrincipal) authentication.getPrincipal()).getRoleType();

        return authTokenService.createOrUpdate(userId, roleType);
    }

//    public RefreshTokenDto refresh(String accessToken, String refreshToken) {
//        return authTokenService.refresh(accessToken, refreshToken);
//    }
}
