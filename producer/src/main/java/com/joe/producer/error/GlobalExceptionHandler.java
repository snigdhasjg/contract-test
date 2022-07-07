package com.joe.producer.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ApiError> clientError(ClientException clientException) {
        ApiError build = ApiError.builder()
                .code(clientException.getCode())
                .message(clientException.getMessage())
                .build();
        return ResponseEntity.status(clientException.getCode().getHttpStatusCode()).body(build);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> anyException(Exception exception) {
        ApiError build = ApiError.builder()
                .code(ErrorCode.INTERNAL_SERVER_ERROR)
                .message("Internal server error. Please contact API service team!!!")
                .build();
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatusCode()).body(build);
    }
}
