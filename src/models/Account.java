package models;

import java.math.BigDecimal;

public class Account {
    private int accountId;
    private String accountNumber;
    private String pin;
    private String name;
    private String mobileNumber;
    private String address;
    private String accountType;
    private BigDecimal balance;

    // Constructor
    public Account(int accountId, String accountNumber, String pin, String name,
                   String mobileNumber, String address, String accountType, BigDecimal balance) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.accountType = accountType;
        this.balance = balance;
    }

    // Getters and Setters
    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}

