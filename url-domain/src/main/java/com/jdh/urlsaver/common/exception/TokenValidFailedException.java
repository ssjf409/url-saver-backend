package com.jdh.urlsaver.common.exception;

import com.jdh.urlsaver.utils.ErrorCode;
import lombok.Getter;

public class TokenValidFailedException extends BaseException {

    @Getter
    private final ErrorCode code = ErrorCode.INVALID_TOKEN;

    public TokenValidFailedException() {
        super();
    }

    public TokenValidFailedException(String message) {
        super(message);
    }

    public TokenValidFailedException(String errorCode, String message) {
        super(errorCode, message);
    }
}