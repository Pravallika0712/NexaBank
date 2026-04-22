package com.bank.service;

import com.bank.model.User;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    @Autowired private UserRepository userRepo;
    public User register(User user) {

        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        return userRepo.save(user);
    }
    public Map<String, Object> login(String username, String password) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        Map<String, Object> res = new HashMap<>();
        res.put("token", user.getId());
        res.put("userId", user.getId());

        return res;
    }
}