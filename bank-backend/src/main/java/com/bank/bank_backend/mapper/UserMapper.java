package com.bank.bank_backend.mapper;

import com.bank.bank_backend.dto.RegisterRequest;
import com.bank.bank_backend.entity.User;

public class UserMapper {

    public static User toEntity(RegisterRequest req) {
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        return user;
    }
}