package com.bank.bank_backend.controller;

import org.springframework.web.bind.annotation.*;

import com.bank.bank_backend.entity.Account;
import com.bank.bank_backend.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Account create(@RequestParam String email) {
        return service.createAccount(email);
    }

    @GetMapping("/{accNo}")
    public Account get(@PathVariable String accNo) {
        return service.getAccount(accNo);
    }
}