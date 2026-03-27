package com.bank.bank_backend.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.bank.bank_backend.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntime(RuntimeException ex) {
        return new ResponseEntity<>(
                new ApiResponse(ex.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleAll(Exception ex) {
        return new ResponseEntity<>(
                new ApiResponse("Something went wrong"),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}