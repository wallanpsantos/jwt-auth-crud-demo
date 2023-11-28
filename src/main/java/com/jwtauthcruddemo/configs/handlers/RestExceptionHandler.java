package com.jwtauthcruddemo.configs.handlers;

import com.jwtauthcruddemo.dtos.output.ErrorDto;
import com.jwtauthcruddemo.exceptions.AppLoginException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {AppLoginException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppLoginException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorDto(ex.getMessage()));
    }

}
