package com.javamentor.qa.platform.exception;

import io.swagger.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionApiHandler {
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> defaultExceptionHandler(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.OK);
    }
}
