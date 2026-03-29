package com.bank.bank_backend.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // CREDIT / DEBIT / TRANSFER
    private Double amount;
    private LocalDateTime timestamp;

    // Many Transactions -> One Sender Account
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    // Many Transactions -> One Receiver Account
    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;
}

