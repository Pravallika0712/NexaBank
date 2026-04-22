package com.bank.service;

import com.bank.model.*;
import com.bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FDService {

    @Autowired private FixedDepositRepository fdRepo;
    @Autowired private AccountRepository accRepo;
    public FixedDeposit createFD(String userId, String accNum, double amount, int months) {

        if (amount <= 0 || months <= 0)
            throw new RuntimeException("Invalid FD details");

        Account acc = accRepo.findByAccountNumber(accNum)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (acc.getBalance() < amount)
            throw new RuntimeException("Insufficient balance");

        double rate = 7;

        double maturity = amount * Math.pow(1 + rate / 100, months / 12.0);
        acc.setBalance(acc.getBalance() - amount);
        accRepo.save(acc);

        FixedDeposit fd = new FixedDeposit();
        fd.setUserId(userId);
        fd.setAccountNumber(accNum);
        fd.setPrincipal(amount);
        fd.setInterestRate(rate);
        fd.setTenureMonths(months);
        fd.setMaturityAmount(maturity);
        fd.setWithdrawn(false);

        return fdRepo.save(fd);
    }

    public FixedDeposit breakFD(String fdId) {

        FixedDeposit fd = fdRepo.findById(fdId)
                .orElseThrow(() -> new RuntimeException("FD not found"));

        if (fd.isWithdrawn())
            throw new RuntimeException("FD already withdrawn");

        Account acc = accRepo.findByAccountNumber(fd.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        acc.setBalance(acc.getBalance() + fd.getMaturityAmount());
        accRepo.save(acc);

        fd.setWithdrawn(true);

        return fdRepo.save(fd);
    }
    public List<FixedDeposit> getUserFDs(String userId) {
        return fdRepo.findByUserId(userId);
    }

    
}