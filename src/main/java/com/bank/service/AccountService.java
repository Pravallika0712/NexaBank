package com.bank.service;
import com.bank.model.*;
import com.bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    @Autowired private UserRepository userRepo;
    @Autowired private AccountRepository accRepo;

    public Account createAccount(String userId, String type, double amount) {

        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Account acc = new Account();

        acc.setUser(user);
        acc.setType(AccountType.valueOf(type.toUpperCase()));
        acc.setBalance(amount);
        acc.setActive(true);

        acc.setAccountNumber("ACC" + System.currentTimeMillis());

        return accRepo.save(acc);
    }

    public List<Account> getAccounts(String userId) {
        return accRepo.findByUser_Id(userId);
    }
}