package com.jdh.urlsaver.utils;

public enum ErrorCode {
    INVALID_TOKEN("40101", "invalid_token"),
    UNAUTHORIZED("40101", "unauthorized"),
    ;

    private final String code;
    private final String messageCode;

    ErrorCode(String code, String messageCode) {
        this.code = code;
        this.messageCode = messageCode;
    }

    public String getCode() {
        return code;
    }

    public String getMessageCode() {
        return messageCode;
    }
}