package com.app.demo;

public class Account {
    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    // synchronized to avoid race conditions
    public synchronized void withdraw(String user, double amount) {
        System.out.println(user + " is trying to withdraw ₹" + amount);
        if (balance >= amount) {
            System.out.println(user + " proceeding...");
            try {
                Thread.sleep(1000); // simulate time delay
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            balance -= amount;
            System.out.println(user + " completed withdrawal. Remaining balance: ₹" + balance);
        } else {
            System.out.println("❌ " + user + " - Not enough balance!");
        }
    }

    public double getBalance() {
        return balance;
    }
}
