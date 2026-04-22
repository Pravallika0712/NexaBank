package com.bank.controller;

import com.bank.model.Transaction;
import com.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TransactionController {

    @Autowired private TransactionService transactionService;

    @PostMapping("/deposit")
    public Transaction deposit(@RequestBody Map<String, String> body) {
        return transactionService.deposit(
                body.get("accountNumber"),
                Double.parseDouble(body.get("amount")));
    }

    @PostMapping("/withdraw")
    public Transaction withdraw(@RequestBody Map<String, String> body) {
        return transactionService.withdraw(
                body.get("accountNumber"),
                Double.parseDouble(body.get("amount")));
    }

    @PostMapping("/transfer")
    public Transaction transfer(@RequestBody Map<String, String> body) {
        return transactionService.transfer(
                body.get("fromAccount"),
                body.get("toAccount"),
                Double.parseDouble(body.get("amount")));
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@RequestParam String accountNumber) {
        return transactionService.getTransactions(accountNumber);
    }
}