package com.bank.bank_backend.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.bank.bank_backend.entity.User;
import com.bank.bank_backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                // ✅ FIX HERE
                .roles(user.getRole().name().replace("ROLE_", ""))
                .build();
    }
}