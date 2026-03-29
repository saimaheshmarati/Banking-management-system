package com.bank.bank_backend.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@ToString(exclude = "accounts")
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private String accountType;
    private Double balance;
    private String status;

    // Many Accounts -> One User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // One Account -> Many Transactions (Sent)
    @OneToMany(mappedBy = "fromAccount")
    private List<Transaction> sentTransactions;

    // One Account -> Many Transactions (Received)
    @OneToMany(mappedBy = "toAccount")
    private List<Transaction> receivedTransactions;
}