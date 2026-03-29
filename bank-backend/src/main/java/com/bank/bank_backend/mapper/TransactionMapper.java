package com.bank.bank_backend.mapper;

import java.time.LocalDateTime;

import com.bank.bank_backend.dto.TransactionResponse;
import com.bank.bank_backend.entity.Account;
import com.bank.bank_backend.entity.Transaction;

public class TransactionMapper {

    // Deposit
    public static Transaction deposit(Account acc, Double amount) {

        Transaction txn = new Transaction();
        txn.setType("CREDIT");
        txn.setAmount(amount);
        txn.setTimestamp(LocalDateTime.now());
        txn.setToAccount(acc);

        return txn;
    }

    // Withdraw
    public static Transaction withdraw(Account acc, Double amount) {

        Transaction txn = new Transaction();
        txn.setType("DEBIT");
        txn.setAmount(amount);
        txn.setTimestamp(LocalDateTime.now());
        txn.setFromAccount(acc);

        return txn;
    }

    // Transfer
    public static Transaction transfer(Account from, Account to, Double amount) {

        Transaction txn = new Transaction();
        txn.setType("TRANSFER");
        txn.setAmount(amount);
        txn.setTimestamp(LocalDateTime.now());
        txn.setFromAccount(from);
        txn.setToAccount(to);

        return txn;
    }

    // ENTITY -> RESPONSE DTO
    public static TransactionResponse toResponse(Transaction txn) {

        return TransactionResponse.builder()
                .id(txn.getId())
                .type(txn.getType())
                .amount(txn.getAmount())
                .timestamp(txn.getTimestamp())
                .fromAccount(txn.getFromAccount() != null ? txn.getFromAccount().getAccountNumber() : null)
                .toAccount(txn.getToAccount() != null ? txn.getToAccount().getAccountNumber() : null)
                .build();
    }
}