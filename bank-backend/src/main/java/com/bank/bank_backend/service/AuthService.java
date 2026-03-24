package com.bank.bank_backend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.bank_backend.dto.AuthRequest;
import com.bank.bank_backend.dto.AuthResponse;
import com.bank.bank_backend.dto.RegisterRequest;
import com.bank.bank_backend.entity.User;
import com.bank.bank_backend.repository.UserRepository;
import com.bank.bank_backend.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder;

    public AuthService(UserRepository userRepo,
                       JwtUtil jwtUtil,
                       BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.encoder = encoder;
    }

    // ✅ REGISTER
    public String register(RegisterRequest req) {

        // 🔴 Check if email exists
        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());

        // 🔐 Encrypt password
        user.setPassword(encoder.encode(req.getPassword()));

        user.setRole("ROLE_USER");

        userRepo.save(user);

        return "User Registered Successfully";
    }

    // ✅ LOGIN
    public AuthResponse login(AuthRequest req) {

        User user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔐 Check encrypted password
        if (!encoder.matches(req.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, "Login Successful");
    }
}