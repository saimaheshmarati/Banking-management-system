package com.bank.bank_backend.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.bank.bank_backend.entity.User;
import com.bank.bank_backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepo;

    private User user;
    
    public User getUser() {
        return user;
    }

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(user);
    }
}