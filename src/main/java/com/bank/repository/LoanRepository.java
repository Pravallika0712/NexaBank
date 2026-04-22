package com.bank.repository;

import com.bank.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, String> {
	List<Loan> findByUser_Id(String userId);
    
}
