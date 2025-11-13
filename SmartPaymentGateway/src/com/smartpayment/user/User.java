package com.smartpayment.user;

public class User {
    private String name;
    private double upiBalance = 50000;
    private double walletBalance = 10000;
    private double creditLimit = 50000;

    public User(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public double getUpiBalance() { return upiBalance; }
    public double getWalletBalance() { return walletBalance; }
    public double getCreditLimit() { return creditLimit; }

    public void deductUpi(double amount) { upiBalance -= amount; }
    public void deductWallet(double amount) { walletBalance -= amount; }
    public void deductCredit(double amount) { creditLimit -= amount; }
}

