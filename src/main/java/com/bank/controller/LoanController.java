package com.bank.controller;

import com.bank.model.Loan;
import com.bank.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LoanController {

    @Autowired private LoanService loanService;

    @PostMapping("/loan/apply")
    public Loan applyLoan(@RequestHeader("Authorization") String token,
                          @RequestBody Map<String, String> body) {

    	String userId = token.replace("Bearer ", "");

        return loanService.applyLoan(
                userId,
                body.get("accountNumber"),
                Double.parseDouble(body.get("amount")),
                Integer.parseInt(body.get("months"))
        );
    }

    @PostMapping("/loan/pay")
    public Loan pay(@RequestHeader("Authorization") String token,
                    @RequestBody Map<String, String> body) {

        return loanService.payEmi(body.get("loanId"));
    }


    @GetMapping("/loans")
    public List<Loan> getLoans(@RequestHeader("Authorization") String token) {
        String userId = token.replace("Bearer ", "");  
        return loanService.getUserLoans(userId);
    }
    
    
    
    
}