package services;

import database.DbConnection;
import java.sql.*;
import java.security.SecureRandom;
import java.util.Scanner;

public class AccountService {
    private Connection conn;
    private Scanner sc;
    private static final SecureRandom secureRandom = new SecureRandom();

    public AccountService() {
        conn = DbConnection.getConnection();
        sc = new Scanner(System.in);
    }

    // Generate 9-digit account number
    private String generateAccountNumber() {
        return String.format("%09d", secureRandom.nextInt(1_000_000_000));
    }

    // Generate 4-digit PIN
    private String generatePin() {
        return String.format("%04d", secureRandom.nextInt(10_000));
    }

    // CREATE ACCOUNT
    public void createAccount() {
        try {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Mobile Number: ");
            String mobile = sc.nextLine();

            System.out.print("Enter Address: ");
            String address = sc.nextLine();

            System.out.print("Enter Account Type (SAVINGS/CURRENT/FIXED): ");
            String accountType = sc.nextLine().toUpperCase();

            String accountNumber = generateAccountNumber();
            String pin = generatePin();

            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO accounts (account_number, pin, name, mobile_number, address, account_type, balance) VALUES (?, ?, ?, ?, ?, ?, 0)"
            );
            stmt.setString(1, accountNumber);
            stmt.setString(2, pin);
            stmt.setString(3, name);
            stmt.setString(4, mobile);
            stmt.setString(5, address);
            stmt.setString(6, accountType);

            stmt.executeUpdate();

            System.out.println("✅ Account created successfully!");
            System.out.println("Your Account Number: " + accountNumber);
            System.out.println("Your PIN: " + pin);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // DEPOSIT
    public void deposit() {
        try {
            System.out.print("Enter Account Number: ");
            String accNumber = sc.nextLine();

            System.out.print("Enter PIN: ");
            String pin = sc.nextLine();

            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();
            sc.nextLine(); // clear buffer

            conn.setAutoCommit(false);

            PreparedStatement authStmt = conn.prepareStatement(
                "SELECT account_id FROM accounts WHERE account_number = ? AND pin = ?"
            );
            authStmt.setString(1, accNumber);
            authStmt.setString(2, pin);
            ResultSet authRs = authStmt.executeQuery();

            if (authRs.next()) {
                int accountId = authRs.getInt("account_id");

                PreparedStatement updateBal = conn.prepareStatement(
                    "UPDATE accounts SET balance = balance + ? WHERE account_id = ?"
                );
                updateBal.setDouble(1, amount);
                updateBal.setInt(2, accountId);
                updateBal.executeUpdate();

                PreparedStatement insertTx = conn.prepareStatement(
                    "INSERT INTO transactions (account_id, type, amount) VALUES (?, 'DEPOSIT', ?)"
                );
                insertTx.setInt(1, accountId);
                insertTx.setDouble(2, amount);
                insertTx.executeUpdate();

                conn.commit();
                System.out.println("✅ Deposit successful!");
            } else {
                System.out.println("❌ Invalid account number or PIN.");
                conn.rollback();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            try { conn.rollback(); } catch (Exception ex) {}
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception e) {}
        }
    }

    // WITHDRAW
    public void withdraw() {
        try {
            System.out.print("Enter Account Number: ");
            String accNumber = sc.nextLine();

            System.out.print("Enter PIN: ");
            String pin = sc.nextLine();

            System.out.print("Enter Amount: ");
            double amount = sc.nextDouble();
            sc.nextLine(); // clear buffer

            conn.setAutoCommit(false);

            PreparedStatement authStmt = conn.prepareStatement(
                "SELECT account_id, balance FROM accounts WHERE account_number = ? AND pin = ?"
            );
            authStmt.setString(1, accNumber);
            authStmt.setString(2, pin);
            ResultSet authRs = authStmt.executeQuery();

            if (authRs.next()) {
                int accountId = authRs.getInt("account_id");
                double balance = authRs.getDouble("balance");

                if (balance >= amount) {
                    PreparedStatement updateBal = conn.prepareStatement(
                        "UPDATE accounts SET balance = balance - ? WHERE account_id = ?"
                    );
                    updateBal.setDouble(1, amount);
                    updateBal.setInt(2, accountId);
                    updateBal.executeUpdate();

                    PreparedStatement insertTx = conn.prepareStatement(
                        "INSERT INTO transactions (account_id, type, amount) VALUES (?, 'WITHDRAW', ?)"
                    );
                    insertTx.setInt(1, accountId);
                    insertTx.setDouble(2, amount);
                    insertTx.executeUpdate();

                    conn.commit();
                    System.out.println("✅ Withdrawal successful!");
                } else {
                    System.out.println("❌ Insufficient balance.");
                    conn.rollback();
                }
            } else {
                System.out.println("❌ Invalid account number or PIN.");
                conn.rollback();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            try { conn.rollback(); } catch (Exception ex) {}
        } finally {
            try { conn.setAutoCommit(true); } catch (Exception e) {}
        }
    }

    // CHECK BALANCE
    public void checkBalance() {
        try {
            System.out.print("Enter Account Number: ");
            String accNumber = sc.nextLine();

            System.out.print("Enter PIN: ");
            String pin = sc.nextLine();

            PreparedStatement stmt = conn.prepareStatement(
                "SELECT name, balance, mobile_number, address, account_type FROM accounts WHERE account_number = ? AND pin = ?"
            );
            stmt.setString(1, accNumber);
            stmt.setString(2, pin);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Mobile: " + rs.getString("mobile_number"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Account Type: " + rs.getString("account_type"));
                System.out.println("Balance: ₹" + rs.getBigDecimal("balance"));
            } else {
                System.out.println("❌ Invalid account number or PIN.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

