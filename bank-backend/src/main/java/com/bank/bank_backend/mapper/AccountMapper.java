package com.bank.bank_backend.mapper;

import java.util.UUID;

import com.bank.bank_backend.entity.Account;
import com.bank.bank_backend.entity.User;

public class AccountMapper {

    public static Account createAccount(User user) {
        Account acc = new Account();
        acc.setAccountNumber(UUID.randomUUID().toString());
        acc.setAccountType("SAVINGS");
        acc.setBalance(0.0);
        acc.setStatus("ACTIVE");
        acc.setUser(user);
        return acc;
    }
}