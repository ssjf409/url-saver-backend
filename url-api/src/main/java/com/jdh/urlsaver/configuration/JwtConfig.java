//package com.jdh.urlsaver.configuration;
//
//import com.jdh.urlsaver.api.service.AuthTokenService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JwtConfig {
//    @Value("${jwt.secret}")
//    private String secret;
//
//    @Bean
//    public AuthTokenService jwtProvider() {
//        return new AuthTokenService(secret);
//    }
//}