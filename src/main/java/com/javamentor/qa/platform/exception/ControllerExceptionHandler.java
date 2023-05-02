package com.javamentor.qa.platform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementException() {
        return new ResponseEntity<>("BadRequest", HttpStatus.BAD_REQUEST);
    }
}
