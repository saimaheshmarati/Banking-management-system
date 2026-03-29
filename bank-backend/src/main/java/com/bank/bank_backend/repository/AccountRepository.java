package com.bank.bank_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.bank_backend.entity.Account;
import com.bank.bank_backend.entity.User;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber); // ✅ REQUIRED
    
    // ✅ Check if account type already exists for a user
    boolean existsByUserAndAccountType(User user, String accountType);
}