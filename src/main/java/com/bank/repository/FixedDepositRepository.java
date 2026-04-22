package com.bank.repository;

import com.bank.model.FixedDeposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FixedDepositRepository extends JpaRepository<FixedDeposit, String> {
    List<FixedDeposit> findByUserId(String userId);
}
