package com.bank.bank_backend.exception;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorResponse<T> {

    private boolean success;
    private String message;
    private T data;
}