package com.jdh.urlsaver.api.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
}
