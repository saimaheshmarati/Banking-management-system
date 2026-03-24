package com.bank.bank_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.bank_backend.dto.TransferRequest;
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
    public String deposit(@RequestParam String accNo,
                          @RequestParam Double amount) {
        return service.deposit(accNo, amount);
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestParam String accNo,
                           @RequestParam Double amount) {
        return service.withdraw(accNo, amount);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest req) {
        return service.transfer(req);
    }

    @GetMapping("/history/{accNo}")
    public List<Transaction> history(@PathVariable String accNo) {
        return service.getHistory(accNo);
    }
}