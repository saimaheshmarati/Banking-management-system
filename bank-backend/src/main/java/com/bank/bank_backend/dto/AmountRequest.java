package com.bank.bank_backend.dto;

import lombok.Data;

@Data
public class AmountRequest {

    private String accountNumber;
    private Double amount;
}