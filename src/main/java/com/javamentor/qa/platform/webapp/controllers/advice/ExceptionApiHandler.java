package com.javamentor.qa.platform.webapp.controllers.advice;

import com.javamentor.qa.platform.exception.ApiRequestException;
import com.javamentor.qa.platform.exception.ConstrainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<ApiRequestException> apiRequestException(ApiRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiRequestException(e.getMessage()));
    }

    @ExceptionHandler(ConstrainException.class)
    public ResponseEntity<ConstrainException> constrainException(ConstrainException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ConstrainException(e.getMessage()));
    }
    
}