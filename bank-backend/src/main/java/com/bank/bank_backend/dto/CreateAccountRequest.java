package com.bank.bank_backend.dto;


import lombok.Data;

@Data
public class CreateAccountRequest {
    private String email;
    private String accountType;   // SAVINGS / CURRENT
    private Double initialDeposit;
}