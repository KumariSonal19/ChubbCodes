package com.example.transfer;

public interface AccountService {
    boolean exists(String accountNumber);
    double getBalance(String accountNumber);
    boolean debit(String accountNumber, double amount);
    boolean credit(String accountNumber, double amount);
}
