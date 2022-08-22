package com.jdh.urlsaver.domain.common.exception;

public class BaseException extends RuntimeException {

    public final String errorCode;
    public final String message;

    public BaseException() {
        super();
        errorCode = null;
        message = null;
    }

    public BaseException(String message) {
        this.errorCode = null;
        this.message = message;
    }

    public BaseException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
