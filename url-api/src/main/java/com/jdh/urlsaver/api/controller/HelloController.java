package com.jdh.urlsaver.api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        String loginIdMessage = userPrincipal.getUsername() == null? "" : "userId: " + userPrincipal.getUsername();
        return "hello stranger~ " + loginIdMessage;
    }
}
