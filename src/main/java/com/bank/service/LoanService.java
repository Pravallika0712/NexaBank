package com.bank.service;

import com.bank.model.*;
import com.bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepo;

    @Autowired
    private AccountRepository accRepo;
    public Loan applyLoan(String userId, String accNum, double amount, int months) {

        if (amount <= 0 || months <= 0)
            throw new RuntimeException("Invalid loan details");

        Account acc = accRepo.findByAccountNumber(accNum)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!acc.getUser().getId().equals(userId)) {
            throw new RuntimeException("Account does not belong to user");
        }

        double annualRate = 10.0;
        double r = annualRate / 100 / 12;

        double emi = amount * r * Math.pow(1 + r, months) /
                (Math.pow(1 + r, months) - 1);

        acc.setBalance(acc.getBalance() + amount);
        accRepo.save(acc);

        Loan loan = new Loan();

        loan.setUser(acc.getUser());
        loan.setAccount(acc);

        loan.setPrincipal(amount);
        loan.setInterestRate(annualRate);
        loan.setTenureMonths(months);
        loan.setEmi(emi);
        loan.setTotalPayable(emi * months);
        loan.setOutstanding(emi * months);
        loan.setStatus(LoanStatus.ACTIVE);

        return loanRepo.save(loan);
    }

    public Loan payEmi(String loanId) {

        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStatus() == LoanStatus.CLOSED)
            throw new RuntimeException("Loan already closed");
        Account acc = loan.getAccount();

        if (acc.getBalance() < loan.getEmi())
            throw new RuntimeException("Insufficient balance");

        acc.setBalance(acc.getBalance() - loan.getEmi());
        accRepo.save(acc);

        loan.setOutstanding(loan.getOutstanding() - loan.getEmi());

        if (loan.getOutstanding() <= 0) {
            loan.setOutstanding(0);
            loan.setStatus(LoanStatus.CLOSED);
        }

        return loanRepo.save(loan);
    }
    public List<Loan> getUserLoans(String userId) {
        return loanRepo.findByUser_Id(userId);
    }
}