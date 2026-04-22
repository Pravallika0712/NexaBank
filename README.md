# 🏦 NexaBank Pro — Enhanced Java Banking System

A full-stack banking simulation built with **Spring Boot + MySQL**, featuring Loans, Fixed Deposits, Savings Growth Charts, EMI Calculator, and rich analytics.

🎥 **[Watch Demo Video](https://drive.google.com/file/d/1YdVA0BCtliAYmqTBC4OyIxUBXHKPTxVg/view?usp=sharing)**

---

## ✨ Features

### Core Banking
- 🔐 Secure login & registration with role-based access
- 💳 Savings & Current accounts (multiple per user)
- 💰 Deposit / Withdraw / Transfer
- 📋 Full transaction history with status tracking

### 🆕 Advanced Features

| Feature | Description |
|---|---|
| 🏦 **Loan System** | Apply for Home / Personal / Vehicle / Education / Business loans with auto EMI calculation |
| 💸 **EMI Payments** | Pay EMI month by month, visual progress bar, remaining EMI count |
| 🔒 **Fixed Deposits** | Create FDs, live maturity preview, compound interest (quarterly), break FD anytime |
| 📈 **Savings Growth Chart** | 7-year projection line chart comparing growth vs. flat balance |
| 🧮 **EMI Calculator** | Standalone calculator with pie chart (principal vs. interest) + monthly amortization table |
| 📊 **Analytics** | Doughnut chart by category, bar chart (in vs. out), detailed category breakdown |
| 📉 **FD Portfolio Chart** | Stacked bar chart showing principal + interest per FD |

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 4.0.5 |
| Database | MySQL 8+ |
| ORM | Spring Data JPA / Hibernate |
| Frontend | HTML, CSS, JavaScript (SPA) |
| Build Tool | Maven |

---


## 🚀 How to Run

### Step 1 — Set up MySQL Database

Open MySQL Workbench or your MySQL terminal and run:

```sql
CREATE DATABASE bankdb;
```

### Step 2 — Configure Database Credentials

Open `src/main/resources/application.properties` and update with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bankdb
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.globally_quoted_identifiers=true
```

> ⚠️ **Security Note:** Never push your real password to GitHub. Use environment variables or a `.env` file (see below).

### Step 3 — Build & Run

**Using Maven Wrapper (recommended):**

```bash
# Windows
mvnw.cmd spring-boot:run

# Mac / Linux
chmod +x mvnw
./mvnw spring-boot:run
```

**Or using Maven directly:**

```bash
mvn spring-boot:run
```

### Step 4 — Open in Browser

```
http://localhost:8080
```

---

## 📁 Project Structure

```
banking-system/
├── src/
│   ├── main/
│   │   ├── java/com/bank/
│   │   │   ├── BankingSystemApplication.java     ← Entry point
│   │   │   ├── controller/
│   │   │   │   ├── AccountController.java
│   │   │   │   ├── AnalyticsController.java
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── EMIController.java
│   │   │   │   ├── FDController.java
│   │   │   │   ├── LoanController.java
│   │   │   │   └── TransactionController.java
│   │   │   ├── model/
│   │   │   │   ├── Account.java
│   │   │   │   ├── FixedDeposit.java
│   │   │   │   ├── Loan.java
│   │   │   │   ├── LoanStatus.java
│   │   │   │   ├── Transaction.java
│   │   │   │   ├── TransactionStatus.java
│   │   │   │   ├── TransactionType.java
│   │   │   │   ├── User.java
│   │   │   │   └── UserRole.java
│   │   │   ├── repository/
│   │   │   │   ├── AccountRepository.java
│   │   │   │   ├── FixedDepositRepository.java
│   │   │   │   ├── LoanRepository.java
│   │   │   │   ├── TransactionRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   └── service/
│   │   │       ├── AccountService.java
│   │   │       ├── AnalyticsService.java
│   │   │       ├── AuthService.java
│   │   │       ├── EMIService.java
│   │   │       ├── FDService.java
│   │   │       ├── LoanService.java
│   │   │       └── TransactionService.java
│   │   └── resources/
│   │       ├── application.properties            
│   │       └── static/
│   │           ├── index.html                    
│   │           └── dashboard.html               
├── pom.xml
└── README.md
```

---

## 🌐 API Reference

| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/register` | Register a new user |
| POST | `/api/login` | Login |
| GET | `/api/accounts` | List all accounts |
| POST | `/api/create-account` | Create new account |
| POST | `/api/deposit` | Deposit funds |
| POST | `/api/withdraw` | Withdraw funds |
| POST | `/api/transfer` | Transfer between accounts |
| GET | `/api/transactions?accountNumber=X` | Transaction history |
| POST | `/api/loan/apply` | Apply for a loan |
| POST | `/api/loan/pay-emi` | Pay EMI installment |
| GET | `/api/loans` | List all loans |
| POST | `/api/loan/calculator` | EMI calculator |
| POST | `/api/fd/create` | Create Fixed Deposit |
| POST | `/api/fd/break` | Break FD early |
| GET | `/api/fds` | List all FDs |
| GET | `/api/analytics` | Spending analytics |
| GET | `/api/savings-growth` | Savings growth projection |

.
