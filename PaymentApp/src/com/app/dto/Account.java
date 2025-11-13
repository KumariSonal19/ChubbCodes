package com.app.dto;

import java.util.Objects;

public class Account {
    private String accountHolderName;
    private String accountNo;
    private String transCode;
    private String country;
    private String ifscCode;
    private double balance;

    public Account(String accountHolderName, String accountNo, String transCode,
                   String country, String ifscCode, double balance) {
        this.accountHolderName = accountHolderName;
        this.accountNo = accountNo;
        this.transCode = transCode;
        this.country = country;
        this.ifscCode = ifscCode;
        this.balance = balance;
    }

    public Account(String accountHolderName, String accountNo, String country, String ifscCode) {
        this(accountHolderName, accountNo, null, country, ifscCode, 0.0);
    }

    public String getAccountHolderName() { return accountHolderName; }
    public String getAccountNo() { return accountNo; }
    public String getTransCode() { return transCode; }
    public String getCountry() { return country; }
    public String getIfscCode() { return ifscCode; }
    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }
    public void credit(double amount) { this.balance += amount; }
    public void debit(double amount) { this.balance -= amount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account other = (Account) o;
        return this.accountNo.equals(other.accountNo)
                && this.accountHolderName.equalsIgnoreCase(other.accountHolderName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountHolderName.toLowerCase(), accountNo);
    }

    @Override
    public String toString() {
        return String.format("%s(%s) IFSC:%s Balance:%.2f",
                accountHolderName, accountNo, ifscCode, balance);
    }
}
