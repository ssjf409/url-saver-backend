package com.jdh.urlsaver.api.service.dto;

import com.jdh.urlsaver.oauth.token.AuthToken;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RefreshTokenDto {
    private final AuthToken accessToken;
    private final AuthToken refreshToken;
    private boolean isNewRefreshToken;
}
