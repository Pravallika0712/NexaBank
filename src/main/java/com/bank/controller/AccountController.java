package com.bank.controller;

import com.bank.model.Account;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AccountController {

    @Autowired private AccountService accountService;

    @PostMapping("/accounts")
    public Account create(@RequestHeader("Authorization") String token,
                          @RequestBody Map<String, Object> body) {

        String userId = token.replace("Bearer ", "").trim(); 
        String type = (String) body.get("type");
        double amount = Double.parseDouble(body.get("initialDeposit").toString());

        return accountService.createAccount(userId, type, amount);
    }

    @GetMapping("/accounts")
    public List<Account> getAccounts(@RequestHeader("Authorization") String token) {
        String userId = token.replace("Bearer ", "");  
        return accountService.getAccounts(userId);
    }
}
