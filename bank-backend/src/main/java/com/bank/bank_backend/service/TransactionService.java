package com.bank.bank_backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.bank_backend.dto.TransferRequest;
import com.bank.bank_backend.entity.Account;
import com.bank.bank_backend.entity.Transaction;
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

    // ✅ Deposit
    public String deposit(String accNo, Double amount) {

        Account acc = accountRepo.findByAccountNumber(accNo)
                .orElseThrow();

        acc.setBalance(acc.getBalance() + amount);
        accountRepo.save(acc);

        Transaction txn = new Transaction();
        txn.setType("DEPOSIT");
        txn.setAmount(amount);
        txn.setTimestamp(LocalDateTime.now());
        txn.setToAccount(accNo);

        txnRepo.save(txn);

        return "Deposit Successful";
    }

    // ✅ Withdraw
    public String withdraw(String accNo, Double amount) {

        Account acc = accountRepo.findByAccountNumber(accNo)
                .orElseThrow();

        if (acc.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        acc.setBalance(acc.getBalance() - amount);
        accountRepo.save(acc);

        Transaction txn = new Transaction();
        txn.setType("WITHDRAW");
        txn.setAmount(amount);
        txn.setTimestamp(LocalDateTime.now());
        txn.setFromAccount(accNo);

        txnRepo.save(txn);

        return "Withdraw Successful";
    }

    // ✅ Transfer (ACID)
    @Transactional
    public String transfer(TransferRequest req) {

        Account sender = accountRepo.findByAccountNumber(req.getFromAccount())
                .orElseThrow();

        Account receiver = accountRepo.findByAccountNumber(req.getToAccount())
                .orElseThrow();

        if (sender.getBalance() < req.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - req.getAmount());
        receiver.setBalance(receiver.getBalance() + req.getAmount());

        accountRepo.save(sender);
        accountRepo.save(receiver);

        Transaction txn = new Transaction();
        txn.setType("TRANSFER");
        txn.setAmount(req.getAmount());
        txn.setTimestamp(LocalDateTime.now());
        txn.setFromAccount(req.getFromAccount());
        txn.setToAccount(req.getToAccount());

        txnRepo.save(txn);

        return "Transfer Successful";
    }

    // ✅ History
    public List<Transaction> getHistory(String accNo) {
        return txnRepo.findAll().stream()
                .filter(t -> accNo.equals(t.getFromAccount())
                          || accNo.equals(t.getToAccount()))
                .toList();
    }
}