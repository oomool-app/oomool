package com.oomool.api.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.oomool.api.global.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<?>> handleBaseException(BaseException ex) {
        return ResponseEntity.status(ex.getStatusCode().getStatusCode())
            .body(ApiResponse.error(ex.getMessage()));
    }

    // FrontEnd에서 검증되어 넘어오기 때문에 fail 메서드는 사용하지 않는다.
    //
    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public ResponseEntity<ApiResponse<?>> handleValidationException(MethodArgumentNotValidException ex) {
    //     Map<String, String> errors = new HashMap<>();
    //     ex.getBindingResult().getAllErrors().forEach((error) -> {
    //         String fieldName = ((FieldError)error).getField();
    //         String errorMessage = error.getDefaultMessage();
    //         errors.put(fieldName, errorMessage);
    //     });
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.fail(errors));
    // }

}
