package com.bank.bank_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BankBackendApplication {

    public static void main(String[] args) {

        // ✅ LOAD .env FILE HERE
        Dotenv dotenv = Dotenv.load();

        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        SpringApplication.run(BankBackendApplication.class, args);
    }
}