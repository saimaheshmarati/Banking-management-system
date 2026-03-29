package com.bank.bank_backend.mapper;


import java.util.UUID;

import com.bank.bank_backend.dto.AccountResponse;
import com.bank.bank_backend.dto.CreateAccountRequest;
import com.bank.bank_backend.entity.Account;
import com.bank.bank_backend.entity.User;

public class AccountMapper {

    // Create Account from request + user
    public static Account createAccount(User user, CreateAccountRequest req) {
        Account acc = new Account();

        acc.setAccountNumber(generateAccountNumber());
        acc.setAccountType(req.getAccountType());
        acc.setBalance(req.getInitialDeposit());
        acc.setStatus("ACTIVE");
        acc.setUser(user);

        return acc;
    }

    // Convert Entity -> Response DTO
    public static AccountResponse mapToResponse(Account acc) {
        return AccountResponse.builder()
                .accountNumber(acc.getAccountNumber())
                .accountType(acc.getAccountType())
                .balance(acc.getBalance())
                .status(acc.getStatus())
                .userEmail(acc.getUser().getEmail())
                .build();
    }

    // Generate unique account number
    private static String generateAccountNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}