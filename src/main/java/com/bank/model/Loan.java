package com.bank.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public LoanStatus getStatus() {
		return status;
	}

	public void setStatus(LoanStatus status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;
	
	private double principal;
	private double interestRate;
	private int tenureMonths;

	private double emi;
	private double totalPayable;
	private double outstanding;

	@Enumerated(EnumType.STRING)
	private LoanStatus status;

	private LocalDateTime createdAt = LocalDateTime.now();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getTenureMonths() {
		return tenureMonths;
	}

	public void setTenureMonths(int tenureMonths) {
		this.tenureMonths = tenureMonths;
	}

	public double getEmi() {
		return emi;
	}

	public void setEmi(double emi) {
		this.emi = emi;
	}

	public double getTotalPayable() {
		return totalPayable;
	}

	public void setTotalPayable(double totalPayable) {
		this.totalPayable = totalPayable;
	}

	public double getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(double outstanding) {
		this.outstanding = outstanding;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}