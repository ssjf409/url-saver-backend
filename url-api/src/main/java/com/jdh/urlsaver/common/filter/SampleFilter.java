package com.jdh.urlsaver.common.filter;

import com.jdh.urlsaver.api.application.AuthApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
@RequiredArgsConstructor
@Component
public class SampleFilter extends OncePerRequestFilter {

    private final AuthApplication authApplication;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

//        Cookie accessTokenCookie = CookieUtil.getCookie(request, TokenType.ACCESS_TOKEN.getHeader()).orElse(null);
//        Cookie refreshTokenCookie = CookieUtil.getCookie(request, TokenType.REFRESH_TOKEN.getHeader()).orElse(null);
//
//        if (accessTokenCookie != null) {
//            authApplication.authenticate(accessTokenCookie.getValue());
//        } else {
//            if (refreshTokenCookie != null) {
//
//            }
//        }


        filterChain.doFilter(request, response);
    }
}