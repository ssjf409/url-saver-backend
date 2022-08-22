package com.jdh.urlsaver.domain.common.exception;

import com.jdh.urlsaver.domain.utils.ErrorCode;
import lombok.Getter;

public class EmailSendException extends BaseException {

    @Getter
    private final ErrorCode code = ErrorCode.UNKNOWN_ERROR;

    public EmailSendException() {
        super();
    }

    public EmailSendException(String message) {
        super(message);
    }

    public EmailSendException(String errorCode, String message) {
        super(errorCode, message);
    }
}