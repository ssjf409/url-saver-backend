package com.jdh.urlsaver.domain.model.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum TokenType {
    ACCESS_TOKEN("access-token"),
    REFRESH_TOKEN("refresh-token"),
    ;

    @Getter
    private final String header;
}
