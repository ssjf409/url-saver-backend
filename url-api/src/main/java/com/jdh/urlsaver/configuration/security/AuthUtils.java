package com.jdh.urlsaver.configuration.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthUtils {
    public static Long getUserId(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return Long.valueOf(userPrincipal.getUsername());
    }
}
