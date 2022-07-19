package com.jdh.urlsaver.api.controller.auth;

import com.jdh.urlsaver.api.application.AuthApplication;
import com.jdh.urlsaver.api.application.dto.LoginResultDto;
import com.jdh.urlsaver.api.entity.auth.LoginRequest;
import com.jdh.urlsaver.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final static String REFRESH_TOKEN = "refresh_token";
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final AuthApplication authApplication;

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestBody LoginRequest loginRequest) {
        LoginResultDto loginResult = authApplication.login(loginRequest);

        int accessTokenCookieMaxAge = (int) loginResult.getAccessTokenExpiry() / 60;
        int refreshTokenCookieMaxAge = (int) loginResult.getRefreshTokenExpiry() / 60;
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.deleteCookie(request, response, HEADER_AUTHORIZATION);
        CookieUtil.addCookie(response, HEADER_AUTHORIZATION, loginResult.getAccessToken(), accessTokenCookieMaxAge);
        CookieUtil.addCookie(response, REFRESH_TOKEN, loginResult.getRefreshToken(), refreshTokenCookieMaxAge);

        return ResponseEntity.ok(loginResult.getAccessToken());
    }

//    @GetMapping("/refresh")
//    public ApiResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
//        // access token 확인
//        String accessToken = HeaderUtil.getAccessToken(request);
//        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
//                                        .map(Cookie::getValue)
//                                        .orElse((null));
//
//        RefreshTokenDto refreshTokenDto = authApplication.refresh(accessToken, refreshToken);
//
//        if (refreshTokenDto.isNewRefreshToken()) {
//            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
//            int cookieMaxAge = (int) refreshTokenExpiry / 60;
//            CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
//            CookieUtil.addCookie(response, REFRESH_TOKEN, refreshTokenDto.getRefreshToken().getToken(), cookieMaxAge);
//        }
//
//        return ApiResponse.success("token", refreshTokenDto.getAccessToken().getToken());
//    }
}
