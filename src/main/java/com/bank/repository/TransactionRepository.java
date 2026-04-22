package com.bank.repository;

import com.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    List<Transaction> findByFromAccountOrToAccount(String from, String to);
}