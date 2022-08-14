package com.jdh.urlsaver.common.converter;

import com.jdh.urlsaver.model.entity.auth.Token;
import com.jdh.urlsaver.model.entity.auth.TokenEntity;

public class TokenConverter {
    public static Token convert(TokenEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Token(entity.getSecret(),
                         entity.getType(),
                         entity.getHeader(),
                         entity.getHeaderValue(),
                         entity.getExpiredTime());
    }
}
