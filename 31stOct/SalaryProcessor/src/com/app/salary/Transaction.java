package com.app.salary;

public class Transaction {
    private String sender;
    private String receiver;
    private double amount;
    private String status;

    public Transaction(String sender, String receiver, double amount, String status) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public String toString() {
        return sender + " -> " + receiver + " : " + amount + " (" + status + ")";
    }
}
