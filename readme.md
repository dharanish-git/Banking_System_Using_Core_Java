# 🏦 Banking System

&#x20;&#x20;

A simple **Java-based console Banking System** using **MySQL** for data storage. This project allows users to **create accounts, deposit, withdraw, and check account balance** with authentication using PINs.

---

## 💡 Features

- Create Bank Account (SAVINGS, CURRENT, FIXED)
- Deposit money into accounts
- Withdraw money from accounts
- Check balance with account details
- Secure PIN-based authentication
- Transaction history (deposits & withdrawals)
- Console-based, lightweight, and easy to use

---

## 📂 Project Structure

```
Banking_System/
│
├── src/
│   ├── Main.java               # Entry point
│   ├── database/
│   │   └── DbConnection.java   # Handles MySQL connection
│   ├── models/
│   │   └── Account.java        # Represents a bank account
│   ├── services/
│   │   └── AccountService.java # Logic for account operations
│   └── menus/
│       └── MenuHandler.java    # Displays menus & routes options
│
├── lib/                        # External .jar files (MySQL JDBC driver)
└── README.md                   # Project description
```

---

## 🛠️ Installation & Setup

1. **Clone the repository**

```bash
git clone https://github.com/yourusername/Banking_System.git
cd Banking_System
```

2. **Setup MySQL Database**

```sql
CREATE DATABASE banking_db;

USE banking_db;

CREATE TABLE accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(20) UNIQUE,
    pin VARCHAR(10),
    name VARCHAR(100),
    mobile_number VARCHAR(15),
    address VARCHAR(255),
    account_type VARCHAR(20),
    balance DECIMAL(15,2) DEFAULT 0
);

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id INT,
    type VARCHAR(20),
    amount DECIMAL(15,2),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES accounts(account_id)
);
```

3. **Add MySQL Connector**

- Place the **MySQL JDBC driver** (`mysql-connector-j-9.1.0.jar`) in the `lib/` folder.
- Add it to your project classpath when compiling/running:

```bash
javac -cp "lib/*:." src/**/*.java
java -cp "lib/*:." src/Main
```

---

## 🎮 Usage

1. Run the application.
2. Use the **menu-driven interface**:

```
===== Banking System =====
1. Create Account
2. Deposit
3. Withdraw
4. Check Balance
5. Exit
Choose:
```

3. Follow on-screen instructions to perform operations.

---

## 🔒 Security

- Each account uses a **4-digit PIN** for authentication.
- Transactions are securely recorded in the database.
- Auto-commit is disabled for deposit/withdraw to ensure data consistency.

---

## 📸 Screenshots

*(Add screenshots of your console application here)*

---

## ⚡ Technologies Used

- Java 21
- MySQL 8.0
- JDBC
- SecureRandom for account number & PIN generation
- Console-based user interface

---

## 📄 License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---

Made with ❤️ by **Dharanish**

