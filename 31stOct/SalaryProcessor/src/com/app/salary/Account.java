package com.app.salary;

public class Account {
    private String accountNumber;
    private String name;
    private double balance;

    public Account(String accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    // synchronized to prevent race conditions
    public synchronized boolean transfer(Account toAccount, double amount) {
        if (balance >= amount) {
            balance -= amount;
            toAccount.balance += amount;
            System.out.println(Thread.currentThread().getName() + " transferred " + amount +
                    " from " + name + " to " + toAccount.name);
            return true;
        } else {
            System.out.println(Thread.currentThread().getName() + " failed: Insufficient balance for " + name);
            return false;
        }
    }

    public String getAccountNumber() { return accountNumber; }
    public String getName() { return name; }
    public double getBalance() { return balance; }
}
