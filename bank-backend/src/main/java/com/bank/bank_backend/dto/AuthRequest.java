package com.bank.bank_backend.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}