package com.jdh.urlsaver.common.exception;

import com.jdh.urlsaver.utils.ErrorCode;
import lombok.Getter;

public class UnauthorizedException extends BaseException {

    @Getter
    private final ErrorCode code = ErrorCode.UNAUTHORIZED;

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String errorCode, String message) {
        super(errorCode, message);
    }
}