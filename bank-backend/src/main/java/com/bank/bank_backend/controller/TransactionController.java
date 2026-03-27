package com.bank.bank_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.bank_backend.dto.*;
import com.bank.bank_backend.entity.Transaction;
import com.bank.bank_backend.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse> deposit(@RequestParam String accNo,
                                               @RequestParam Double amount) {
        service.deposit(accNo, amount);
        return ResponseEntity.ok(new ApiResponse("Deposit Successful"));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse> withdraw(@RequestParam String accNo,
                                                @RequestParam Double amount) {
        service.withdraw(accNo, amount);
        return ResponseEntity.ok(new ApiResponse("Withdraw Successful"));
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse> transfer(@RequestBody TransferRequest req) {
        service.transfer(req);
        return ResponseEntity.ok(new ApiResponse("Transfer Successful"));
    }

    @GetMapping("/history/{accNo}")
    public ResponseEntity<List<Transaction>> history(@PathVariable String accNo) {
        return ResponseEntity.ok(service.getHistory(accNo));
    }
}