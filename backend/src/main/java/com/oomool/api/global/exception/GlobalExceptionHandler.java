package com.oomool.api.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException ex) {
        return ResponseEntity.status(ex.getStatusCode().getStatusCode())
            .body(new ErrorResponse(ex.getStatusCode().getStatusCode(), ex.getStatusCode().getMessage()));
    }
}
