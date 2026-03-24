package com.bank.bank_backend.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bank.bank_backend.entity.Account;
import com.bank.bank_backend.entity.User;
import com.bank.bank_backend.repository.AccountRepository;
import com.bank.bank_backend.repository.UserRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final UserRepository userRepo;

    public AccountService(AccountRepository accountRepo,
                          UserRepository userRepo) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
    }

    // ✅ Create Account
    public Account createAccount(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account acc = new Account();
        acc.setAccountNumber(UUID.randomUUID().toString());
        acc.setAccountType("SAVINGS");
        acc.setBalance(0.0);
        acc.setStatus("ACTIVE");
        acc.setUser(user);

        return accountRepo.save(acc);
    }

    // ✅ ADD THIS METHOD (MISSING)
    public Account getAccount(String accNo) {
        return accountRepo.findByAccountNumber(accNo)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}