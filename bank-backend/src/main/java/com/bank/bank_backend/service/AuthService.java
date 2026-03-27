package com.bank.bank_backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.bank_backend.dto.AuthRequest;
import com.bank.bank_backend.dto.AuthResponse;
import com.bank.bank_backend.dto.RegisterRequest;
import com.bank.bank_backend.entity.User;
import com.bank.bank_backend.mapper.UserMapper;
import com.bank.bank_backend.repository.UserRepository;
import com.bank.bank_backend.security.JwtService;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepo;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;

    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepo,
                       JwtService jwtService,
                       BCryptPasswordEncoder encoder,
                       AuthenticationManager authenticationManager) {

        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
    }

    // ✅ REGISTER
 // ✅ REGISTER
    public String register(RegisterRequest req) {

        if (userRepo.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = UserMapper.toEntity(req);

        user.setPassword(encoder.encode(req.getPassword()));
        user.setRole("ROLE_USER");

        userRepo.save(user);

        log.info("User registered: {}", req.getEmail());

        return "User Registered Successfully";
    }

    // ✅ LOGIN
    public AuthResponse login(AuthRequest req) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                req.getEmail(),
                req.getPassword()
            )
        );

        log.info("User logged in: {}", req.getEmail());

        String token = jwtService.generateToken(req.getEmail());

        return new AuthResponse(token, "Login Successful");
    }
}