package com.bank.bank_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.bank_backend.dto.AccountResponse;
import com.bank.bank_backend.dto.CreateAccountRequest;
import com.bank.bank_backend.entity.Account;
import com.bank.bank_backend.entity.User;
import com.bank.bank_backend.exception.ResourceNotFoundException;
import com.bank.bank_backend.exception.InsufficientBalanceException;
import com.bank.bank_backend.exception.InvalidRequestException;
import com.bank.bank_backend.mapper.AccountMapper;
import com.bank.bank_backend.repository.AccountRepository;
import com.bank.bank_backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

    private final AccountRepository accountRepo;
    private final UserRepository userRepo;

    public AccountService(AccountRepository accountRepo,
                          UserRepository userRepo) {
        this.accountRepo = accountRepo;
        this.userRepo = userRepo;
    }

    
    // Create Account
    @Transactional
    public AccountResponse createAccount(CreateAccountRequest req, User loggedInUser) {
    	
//    	System.out.println(loggedInUser);

        if (req.getEmail() == null || req.getEmail().isEmpty()) {
            throw new InvalidRequestException("Email is required");
        }

        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (req.getInitialDeposit() == null || req.getInitialDeposit() < 500) {
            throw new InvalidRequestException("Minimum balance is 500");
        }

        if (!req.getEmail().equalsIgnoreCase(loggedInUser.getEmail())) {
            throw new InvalidRequestException("Not allowed to create account for another user");
        }

        boolean exists = accountRepo
                .existsByUserAndAccountType(user, req.getAccountType());
        System.out.println(exists);

        if (exists) {
            throw new InvalidRequestException("Account type already exists");
            
        }

        Account acc = AccountMapper.createAccount(user, req);
        Account saved = accountRepo.save(acc);

        return AccountMapper.mapToResponse(saved);
    }

    // Get Account
    public AccountResponse getAccount(String accNo) {

        if (accNo == null || accNo.isEmpty()) {
            throw new InvalidRequestException("Account number is required");
        }

        Account acc = accountRepo.findByAccountNumber(accNo)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (!"ACTIVE".equalsIgnoreCase(acc.getStatus())) {
            throw new InvalidRequestException("Account is not active");
        }

        return AccountMapper.mapToResponse(acc);
    }
    
    
//    //checkbalance
//    public Double checkBalance(String accountNumber, User loggedInUser) {
//
//        Account acc = accountRepo.findByAccountNumber(accountNumber)
//                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
//
//        // SECURITY CHECK
//        if (!acc.getUser().getId().equals(loggedInUser.getId())) {
//            throw new RuntimeException("Not allowed to access this account");
//        }
//
//        if (!"ACTIVE".equalsIgnoreCase(acc.getStatus())) {
//            throw new RuntimeException("Account is not active");
//        }
//
//        return acc.getBalance();
//    }
    
    //get my accounts
    public List<AccountResponse> getAccountsByUser(User user) {

        List<Account> accounts = accountRepo.findByUser(user);

        return accounts.stream()
                .map(AccountMapper::mapToResponse)
                .toList();
    }
}