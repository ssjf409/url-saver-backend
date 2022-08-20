package com.jdh.urlsaver.common.filter;

import com.jdh.urlsaver.api.application.TokenApplication;
import com.jdh.urlsaver.api.service.dto.AuthResult;
import com.jdh.urlsaver.configuration.properties.AppProperties;
import com.jdh.urlsaver.model.entity.auth.TokenType;
import com.jdh.urlsaver.utils.CookieUtil;
import io.vavr.Tuple2;
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

import static com.jdh.urlsaver.model.entity.auth.TokenType.*;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenApplication tokenApplication;
    private final AppProperties appProperties;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

//        String accessTokenStr = HeaderUtil.getAccessToken(request);
        Cookie accessTokenCookie = CookieUtil.getCookie(request, "access-token").orElse(null);
        Cookie refreshTokenCookie = CookieUtil.getCookie(request, "refresh-token").orElse(null);
        String accessTokenStr = accessTokenCookie == null? null : accessTokenCookie.getValue();
        String refreshTokenStr = refreshTokenCookie == null? null : refreshTokenCookie.getValue();
//        AuthToken token = tokenProvider.convertAuthToken(accessTokenStr);
        if (tokenApplication.validate(accessTokenStr)) {
            Authentication authentication = tokenApplication.getAuthentication(accessTokenStr);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (tokenApplication.validate(refreshTokenStr)) {
            Tuple2<Authentication, AuthResult> refreshed = tokenApplication.refreshAuthentication(refreshTokenStr);

            CookieUtil.deleteCookie(request, response, ACCESS_TOKEN.getHeader());
            CookieUtil.addCookie(response, ACCESS_TOKEN.getHeader(), refreshed._2.getAccessToken().getToken(),
                                 appProperties.getAuth().getTokenExpirySec());
            CookieUtil.deleteCookie(request, response, TokenType.REFRESH_TOKEN.getHeader());
            CookieUtil.addCookie(response, TokenType.REFRESH_TOKEN.getHeader(),
                                 refreshed._2.getRefreshToken().getToken(),
                                 appProperties.getAuth().getRefreshTokenExpirySec());

            SecurityContextHolder.getContext().setAuthentication(refreshed._1);
        }

        filterChain.doFilter(request, response);
    }
}
