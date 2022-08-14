package com.jdh.urlsaver.api.service.dto;

import com.jdh.urlsaver.model.entity.auth.Token;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthResult {
    private final Token accessToken;
    private final Token refreshToken;
}
