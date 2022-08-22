package com.jdh.urlsaver.domain.common.response;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Error {

    private String code;
    private String message;

    public Error(String code) {
        this.code = code;
//            this.message = MsgTranslator.getErrorMessage(code);
//            this.message = MessageSourceTranslator.getStaticMessage()
    }

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
//            this.message = MsgTranslator.getErrorMessage(code) + " (" + message + ")";
    }

    @Override
    public String toString() {
        return "Error{" +
               "code='" + code + '\'' +
               ", description='" + message + '\'' +
               '}';
    }
}