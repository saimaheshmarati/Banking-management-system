package com.bank.bank_backend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionResponse {

    private Long id;
    private String type;
    private Double amount;
    private LocalDateTime timestamp;

    private String fromAccount;
    private String toAccount;
}