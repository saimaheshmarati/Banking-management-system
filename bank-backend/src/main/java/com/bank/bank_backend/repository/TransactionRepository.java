package com.bank.bank_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.bank_backend.entity.Transaction; // ✅ CORRECT IMPORT

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByFromAccount_AccountNumberOrToAccount_AccountNumber(
            String fromAccount,
            String toAccount
    );
}