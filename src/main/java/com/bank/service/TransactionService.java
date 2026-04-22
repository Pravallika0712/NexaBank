package com.bank.service;

import com.bank.model.*;
import com.bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired private AccountRepository accRepo;
    @Autowired private TransactionRepository txnRepo;
    public Transaction deposit(String accNum, double amount) {

        if (amount <= 0)
            throw new RuntimeException("Amount must be greater than 0");

        Account acc = accRepo.findByAccountNumber(accNum)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        acc.setBalance(acc.getBalance() + amount);
        accRepo.save(acc);

        Transaction t = new Transaction();
        t.setType(TransactionType.DEPOSIT);
        t.setStatus(TransactionStatus.SUCCESS);
        t.setAmount(amount);
        t.setToAccount(accNum);
        t.setDescription("Deposit");

        return txnRepo.save(t);
    }

    public Transaction withdraw(String accNum, double amount) {

        if (amount <= 0)
            throw new RuntimeException("Amount must be greater than 0");

        Account acc = accRepo.findByAccountNumber(accNum)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        AccountType type = acc.getType();

        if (type == AccountType.SAVINGS) {
            if (acc.getBalance() - amount < 500)
                throw new RuntimeException("Minimum balance ₹500 required");

            if (acc.getBalance() < amount)
                throw new RuntimeException("Insufficient balance");
        }

        if (type == AccountType.CURRENT) {
            double overdraftLimit = 50000;

            if (acc.getBalance() + overdraftLimit < amount)
                throw new RuntimeException("Overdraft limit exceeded");
        }

        acc.setBalance(acc.getBalance() - amount);
        accRepo.save(acc);

        Transaction t = new Transaction();
        t.setType(TransactionType.WITHDRAWAL);
        t.setStatus(TransactionStatus.SUCCESS);
        t.setAmount(amount);
        t.setFromAccount(accNum);
        t.setDescription("Withdraw");

        return txnRepo.save(t);	
    }

    public Transaction transfer(String from, String to, double amount) {

        if (amount <= 0)
            throw new RuntimeException("Amount must be greater than 0");

        if (from.equals(to))
            throw new RuntimeException("Cannot transfer to same account");

        Account acc1 = accRepo.findByAccountNumber(from)
                .orElseThrow(() -> new RuntimeException("From account not found"));

        Account acc2 = accRepo.findByAccountNumber(to)
                .orElseThrow(() -> new RuntimeException("To account not found"));

        AccountType type = acc1.getType();
        if (type.equals("SAVINGS")) {
            if (acc1.getBalance() - amount < 500)
                throw new RuntimeException("Minimum balance ₹500 required");

            if (acc1.getBalance() < amount)
                throw new RuntimeException("Insufficient balance");
        }

        if (type.equals("CURRENT")) {
            double overdraftLimit = 50000;

            if (acc1.getBalance() + overdraftLimit < amount)
                throw new RuntimeException("Overdraft limit exceeded");
        }

        acc1.setBalance(acc1.getBalance() - amount);
        acc2.setBalance(acc2.getBalance() + amount);

        accRepo.save(acc1);
        accRepo.save(acc2);

        Transaction out = new Transaction();
        out.setType(TransactionType.TRANSFER_OUT);
        out.setStatus(TransactionStatus.SUCCESS);
        out.setAmount(amount);
        out.setFromAccount(from);
        out.setToAccount(to);
        out.setDescription("Transfer to " + to);

        txnRepo.save(out);
        Transaction in = new Transaction();
        in.setType(TransactionType.TRANSFER_IN);
        in.setStatus(TransactionStatus.SUCCESS);
        in.setAmount(amount);
        in.setFromAccount(from);
        in.setToAccount(to);
        in.setDescription("Received from " + from);

        txnRepo.save(in);

        return out;
    }
    public List<Transaction> getTransactions(String accNum) {
        return txnRepo.findByFromAccountOrToAccount(accNum, accNum);
    }
}