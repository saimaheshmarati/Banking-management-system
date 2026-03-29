package com.bank.bank_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.bank.bank_backend.dto.AccountResponse;
import com.bank.bank_backend.dto.CreateAccountRequest;
import com.bank.bank_backend.entity.Account;
import com.bank.bank_backend.entity.User;
import com.bank.bank_backend.security.CustomUserDetails;
import com.bank.bank_backend.security.CustomUserDetailsService;
import com.bank.bank_backend.service.AccountService;


@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    // Create Account
    @PostMapping("/create")
    public ResponseEntity<AccountResponse> create(@RequestBody CreateAccountRequest req,@AuthenticationPrincipal CustomUserDetails userDetails) {
    	User user = userDetails.getUser();
        return ResponseEntity.ok(service.createAccount(req,user));
    }

    // Get Account
    @GetMapping("/{accNo}")
    public ResponseEntity<AccountResponse> get(@PathVariable String accNo) {
        return ResponseEntity.ok(service.getAccount(accNo));
    }
    
    @GetMapping("/balance/{accNo}")
    public ResponseEntity<Double> checkBalance(
            @PathVariable String accNo,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        User user = userDetails.getUser();

        return ResponseEntity.ok(service.checkBalance(accNo, user));
    }
}