package com.bank.bank_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.bank_backend.dto.AmountRequest;
import com.bank.bank_backend.dto.TransferRequest;
import com.bank.bank_backend.dto.TransactionResponse;
import com.bank.bank_backend.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    // Deposit
    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(@RequestBody AmountRequest req) {
        return ResponseEntity.ok(service.deposit(req));
    }

    // Withdraw
    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@RequestBody AmountRequest req) {
        return ResponseEntity.ok(service.withdraw(req));
    }

    // Transfer
    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody TransferRequest req) {
        return ResponseEntity.ok(service.transfer(req));
    }
    
    //transaction history

    @GetMapping("/{accountNumber}")
    public List<TransactionResponse> getTransactions(
            @PathVariable String accountNumber){

        return service.getTransactions(accountNumber);
    }

}