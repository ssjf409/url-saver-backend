package com.jdh.urlsaver.api.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public final class LoginResultDto {

    private final String accessToken;
    private final long accessTokenExpiry;
    private final String refreshToken;
    private final long refreshTokenExpiry;
}
