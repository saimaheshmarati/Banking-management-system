package com.bank.bank_backend.service;

import org.springframework.stereotype.Service;

import com.bank.bank_backend.entity.Account;
import com.bank.bank_backend.entity.User;
import com.bank.bank_backend.mapper.AccountMapper;
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

        // ✅ USING MAPPER
        Account acc = AccountMapper.createAccount(user);

        return accountRepo.save(acc);
    }

    // ✅ Get Account
    public Account getAccount(String accNo) {
        return accountRepo.findByAccountNumber(accNo)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}