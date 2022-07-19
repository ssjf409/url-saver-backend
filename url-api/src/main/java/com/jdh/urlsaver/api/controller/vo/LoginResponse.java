package com.jdh.urlsaver.api.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public final class LoginResponse {
    private final String accessToken;
}
