package com.bank.bank_backend.mapper;

import java.time.LocalDateTime;

import com.bank.bank_backend.dto.TransferRequest;
import com.bank.bank_backend.entity.Transaction;

public class TransactionMapper {

    public static Transaction deposit(String accNo, Double amount) {
        Transaction txn = new Transaction();
        txn.setType("DEPOSIT");
        txn.setAmount(amount);
        txn.setTimestamp(LocalDateTime.now());
        txn.setToAccount(accNo);
        return txn;
    }

    public static Transaction withdraw(String accNo, Double amount) {
        Transaction txn = new Transaction();
        txn.setType("WITHDRAW");
        txn.setAmount(amount);
        txn.setTimestamp(LocalDateTime.now());
        txn.setFromAccount(accNo);
        return txn;
    }

    public static Transaction transfer(TransferRequest req) {
        Transaction txn = new Transaction();
        txn.setType("TRANSFER");
        txn.setAmount(req.getAmount());
        txn.setTimestamp(LocalDateTime.now());
        txn.setFromAccount(req.getFromAccount());
        txn.setToAccount(req.getToAccount());
        return txn;
    }
}