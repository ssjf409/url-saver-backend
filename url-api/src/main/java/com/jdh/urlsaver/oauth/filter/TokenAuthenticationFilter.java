package com.jdh.urlsaver.oauth.filter;

import com.jdh.urlsaver.api.service.AuthTokenService;
import com.jdh.urlsaver.config.properties.AppProperties;
import com.jdh.urlsaver.oauth.token.AuthToken;
import com.jdh.urlsaver.oauth.token.AuthTokenProvider;
import com.jdh.urlsaver.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String REFRESH_TOKEN = "refresh_token";
    private final AuthTokenProvider authTokenProvider;
    private final AuthTokenService authTokenService;
    private final AppProperties appProperties;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

//        String accessToken = HeaderUtil.getAccessToken(request);

        String accessToken = CookieUtil.getCookie(request, HEADER_AUTHORIZATION)
                                       .map(Cookie::getValue)
                                       .orElse((null));
        String refreshToken = CookieUtil.getCookie(request, REFRESH_TOKEN)
                                        .map(Cookie::getValue)
                                        .orElse((null));

        AuthToken accessAuthToken = authTokenProvider.convertAuthToken(accessToken);
        AuthToken refreshAuthToken = authTokenProvider.convertAuthToken(refreshToken);


        if (accessAuthToken.validate()) {
            Authentication authentication = authTokenProvider.getAuthentication(accessAuthToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
//        else if (refreshAuthToken.validate()) {
//            RefreshTokenDto refreshTokenDto = authTokenService.refresh(accessAuthToken, refreshToken);
//            if (refreshTokenDto.isNewRefreshToken()) {
//                long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();
//                int cookieMaxAge = (int) refreshTokenExpiry / 60;
//                CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
//                CookieUtil.addCookie(response, REFRESH_TOKEN, refreshTokenDto.getRefreshToken().getToken(),
//                                     cookieMaxAge);
//            }
//            Authentication authentication = authTokenProvider.getAuthentication(refreshTokenDto.getAccessToken());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
        filterChain.doFilter(request, response);
    }

}
