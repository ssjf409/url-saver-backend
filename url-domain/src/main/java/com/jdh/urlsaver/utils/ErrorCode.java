package com.jdh.urlsaver.utils;

public enum ErrorCode {
    INVALID_TOKEN("41001", "invalid_token"),
    ;

    private String code;
    private String messageCode;

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