package com.bank.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EMIService {

    public Map<String, Object> calculateEMI(double P, double R, int N) {

        double monthlyRate = R / (12 * 100);

        double emi = (P * monthlyRate * Math.pow(1 + monthlyRate, N)) /
                (Math.pow(1 + monthlyRate, N) - 1);

        double total = emi * N;
        double interest = total - P;

        List<Map<String, Object>> schedule = new ArrayList<>();

        double balance = P;

        for (int i = 1; i <= N; i++) {

            double interestPart = balance * monthlyRate;
            double principalPart = emi - interestPart;

            balance -= principalPart;

            Map<String, Object> row = new HashMap<>();
            row.put("month", i);
            row.put("principal", principalPart);
            row.put("interest", interestPart);
            row.put("balance", Math.max(balance, 0));

            schedule.add(row);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("emi", emi);
        res.put("total", total);
        res.put("totalInterest", interest);
        res.put("schedule", schedule);

        return res;
    }
}