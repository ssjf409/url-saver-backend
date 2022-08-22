package com.jdh.urlsaver.domain.common.exception;

import com.jdh.urlsaver.domain.utils.ErrorCode;
import lombok.Getter;

public class InvalidInputException extends BaseException {

    @Getter
    private final ErrorCode code = ErrorCode.UNKNOWN_ERROR;

    public InvalidInputException() {
        super();
    }

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String errorCode, String message) {
        super(errorCode, message);
    }
}