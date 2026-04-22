package com.bank.service;

import com.bank.model.*;
import com.bank.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalyticsService {

    @Autowired private AccountRepository accRepo;
    @Autowired private TransactionRepository txnRepo;

    public Map<String, Object> analytics(String userId) {

        List<Account> accounts = accRepo.findByUser_Id(userId);

        double totalBalance = accounts.stream()
                .mapToDouble(Account::getBalance)
                .sum();

        double totalIn = 0;
        double totalOut = 0;

        Map<String, Double> categoryMap = new HashMap<>();

        Set<String> processedTxnIds = new HashSet<>(); // ✅ avoid duplicates

        for (Account acc : accounts) {

            List<Transaction> txns =
                    txnRepo.findByFromAccountOrToAccount(
                            acc.getAccountNumber(),
                            acc.getAccountNumber());

            for (Transaction t : txns) {
                if (processedTxnIds.contains(t.getId())) continue;
                processedTxnIds.add(t.getId());
                if (t.getType() == TransactionType.DEPOSIT ||
                    t.getType() == TransactionType.TRANSFER_IN) {

                    totalIn += t.getAmount();
                }
                if (t.getType() == TransactionType.WITHDRAWAL ||
                    t.getType() == TransactionType.TRANSFER_OUT) {

                    totalOut += t.getAmount();

                    String cat = (t.getDescription() == null || t.getDescription().isEmpty())
                            ? "Other"
                            : t.getDescription();

                    categoryMap.put(
                            cat,
                            categoryMap.getOrDefault(cat, 0.0) + t.getAmount()
                    );
                }
            }
        }
        List<Map<String, Object>> categories = new ArrayList<>();

        for (String key : categoryMap.keySet()) {
            Map<String, Object> c = new HashMap<>();
            c.put("name", key);
            c.put("amount", categoryMap.get(key)); 
            categories.add(c);
        }

        Map<String, Object> res = new HashMap<>();
        res.put("totalBalance", totalBalance);
        res.put("totalIn", totalIn);
        res.put("totalOut", totalOut);
        res.put("categories", categories);

        return res;
    }
    public Map<String, Object> getSavingsGrowth(String userId) {

        List<Account> accounts = accRepo.findByUser_Id(userId);

        double total = accounts.stream()
                .filter(a -> a.getType() != null &&
                             a.getType() == AccountType.SAVINGS)
                .mapToDouble(Account::getBalance)
                .sum();

        List<Map<String, Object>> yearly = new ArrayList<>();

        double rate = 4.5 / 100;
        int year = 2026;

        for (int i = 0; i < 7; i++) {

            Map<String, Object> y = new HashMap<>();
            y.put("year", year + i);
            y.put("amount", total);

            yearly.add(y);

            total += total * rate;
        }

        Map<String, Object> res = new HashMap<>();
        res.put("growth", yearly);

        return res;
    }
}