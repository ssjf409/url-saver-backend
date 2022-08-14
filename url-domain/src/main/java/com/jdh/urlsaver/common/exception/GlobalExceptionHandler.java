package com.jdh.urlsaver.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity handleFileException(FileNotFoundException e) {
        log.error(e.getMessage());
        return new ResponseEntity("FileException", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity("Exception", HttpStatus.BAD_REQUEST);
    }


//    private Error toError(ErrorCode errorCode) {
//        try {
//            String message = messageSource.getMessage(
//                    errorCode.getMessageCode(), null, LocaleContextHolder.getLocale());
//            return Response.Error.of(errorCode.getCode(), message);
//        } catch (NoSuchMessageException e) {
//            String message = messageSource.getMessage(
//                    ErrorCode.SYSTEM_UNKNOWN_ERROR.getCode(), null, LocaleContextHolder.getLocale());
//            return Response.Error.of(errorCode.getCode(), message);
//        }
//    }
}