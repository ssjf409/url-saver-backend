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


    @ExceptionHandler(EmailSendException.class)
    protected ResponseEntity<String> handleEmailSendException(EmailSendException e) {
        // TODO: 2022/08/15 have to fix
        log.error(e.getMessage(), e);
        return new ResponseEntity<>("EmailSendException", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e) {
        // TODO: 2022/08/15 have to fix
        log.error(e.getMessage(), e);
        return new ResponseEntity<>("UnauthorizedException", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileNotFoundException.class)
    protected ResponseEntity<String> handleFileException(FileNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>("FileException", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>("Exception", HttpStatus.BAD_REQUEST);
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