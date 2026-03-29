package com.bank.bank_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.bank_backend.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle not found errors
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleNotFound(ResourceNotFoundException ex) {

        ApiErrorResponse<String> response = ApiErrorResponse.<String>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Handle insufficient balance errors
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiErrorResponse<String>> handleBalance(InsufficientBalanceException ex) {

        ApiErrorResponse<String> response = ApiErrorResponse.<String>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    
    // Invalid Request
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ApiErrorResponse<Object>> handleInvalidRequest(InvalidRequestException ex) {

        ApiErrorResponse<Object> response = ApiErrorResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    // Fallback exception
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiErrorResponse<String>> handleGeneric(Exception ex) {
//
//        ApiErrorResponse<String> response = ApiErrorResponse.<String>builder()
//                .success(false)
//                .message("Something went wrong")
//                .data(null)
//                .build();
//
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}