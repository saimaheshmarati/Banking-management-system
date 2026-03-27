package com.bank.bank_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.bank_backend.dto.TransferRequest;
import com.bank.bank_backend.entity.Account;
import com.bank.bank_backend.entity.Transaction;
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

    // ✅ Deposit
    public String deposit(String accNo, Double amount) {

        Account acc = accountRepo.findByAccountNumber(accNo)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        acc.setBalance(acc.getBalance() + amount);
        accountRepo.save(acc);

        // ✅ USING MAPPER
        Transaction txn = TransactionMapper.deposit(accNo, amount);
        txnRepo.save(txn);

        return "Deposit Successful";
    }

    // ✅ Withdraw
    public String withdraw(String accNo, Double amount) {

        Account acc = accountRepo.findByAccountNumber(accNo)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (acc.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        acc.setBalance(acc.getBalance() - amount);
        accountRepo.save(acc);

        // ✅ USING MAPPER
        Transaction txn = TransactionMapper.withdraw(accNo, amount);
        txnRepo.save(txn);

        return "Withdraw Successful";
    }

    // ✅ Transfer (ACID)
    @Transactional
    public String transfer(TransferRequest req) {

        Account sender = accountRepo.findByAccountNumber(req.getFromAccount())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        Account receiver = accountRepo.findByAccountNumber(req.getToAccount())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        if (sender.getBalance() < req.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - req.getAmount());
        receiver.setBalance(receiver.getBalance() + req.getAmount());

        accountRepo.save(sender);
        accountRepo.save(receiver);

        // ✅ USING MAPPER
        Transaction txn = TransactionMapper.transfer(req);
        txnRepo.save(txn);

        return "Transfer Successful";
    }

    // ✅ History
    public List<Transaction> getHistory(String accNo) {
        return txnRepo.findByFromAccountOrToAccount(accNo, accNo);
    }
}