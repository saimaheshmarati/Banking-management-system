package com.bank.bank_backend.service;

import org.springframework.stereotype.Service;

import com.bank.bank_backend.dto.TransferRequest;
import com.bank.bank_backend.dto.AmountRequest;
import com.bank.bank_backend.dto.TransactionResponse;
import com.bank.bank_backend.entity.Account;
import com.bank.bank_backend.entity.Transaction;
import com.bank.bank_backend.entity.User;
import com.bank.bank_backend.exception.ResourceNotFoundException;
import com.bank.bank_backend.exception.InsufficientBalanceException;
import com.bank.bank_backend.mapper.TransactionMapper;
import com.bank.bank_backend.repository.AccountRepository;
import com.bank.bank_backend.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    private final AccountRepository accountRepo;
    private final TransactionRepository txnRepo;

    public TransactionService(AccountRepository accountRepo,
                              TransactionRepository txnRepo) {
        this.accountRepo = accountRepo;
        this.txnRepo = txnRepo;
    }

    // Deposit
    public TransactionResponse deposit(AmountRequest req) {

        Account acc = accountRepo.findByAccountNumber(req.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        acc.setBalance(acc.getBalance() + req.getAmount());
        accountRepo.save(acc);

        Transaction txn = TransactionMapper.deposit(acc, req.getAmount());
        txnRepo.save(txn);

        return TransactionMapper.toResponse(txn);
    }

    // Withdraw
    public TransactionResponse withdraw(AmountRequest req) {

        Account acc = accountRepo.findByAccountNumber(req.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        if (acc.getBalance() < req.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        acc.setBalance(acc.getBalance() - req.getAmount());
        accountRepo.save(acc);

        Transaction txn = TransactionMapper.withdraw(acc, req.getAmount());
        txnRepo.save(txn);

        return TransactionMapper.toResponse(txn);
    }

    // Transfer
    @Transactional
    public TransactionResponse transfer(TransferRequest req) {

        Account sender = accountRepo.findByAccountNumber(req.getFromAccount())
                .orElseThrow(() -> new ResourceNotFoundException("Sender account not found"));

        Account receiver = accountRepo.findByAccountNumber(req.getToAccount())
                .orElseThrow(() -> new ResourceNotFoundException("Receiver account not found"));

        if (sender.getBalance() < req.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - req.getAmount());
        receiver.setBalance(receiver.getBalance() + req.getAmount());

        accountRepo.save(sender);
        accountRepo.save(receiver);

        Transaction txn = TransactionMapper.transfer(sender, receiver, req.getAmount());
        txnRepo.save(txn);

        return TransactionMapper.toResponse(txn);
    }
    
    public Double checkBalance(String accountNumber, User loggedInUser) {

        Account acc = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        // SECURITY CHECK
        if (!acc.getUser().getId().equals(loggedInUser.getId())) {
            throw new RuntimeException("Not allowed to access this account");
        }

        if (!"ACTIVE".equalsIgnoreCase(acc.getStatus())) {
            throw new RuntimeException("Account is not active");
        }

        return acc.getBalance();
    }
}