package com.jdh.urlsaver.api.application.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthVo {
    private String accessToken;
    private String refreshToken;
}
