package com.app.demo;

public class BankDemo {

    public static void main(String[] args) {
        Account acc = new Account(1000); // start with ₹1000

        // Thread 1 (User A)
        Thread user1 = new Thread(() -> {
            acc.withdraw("User A", 700);
        });

        // Thread 2 (User B)
        Thread user2 = new Thread(() -> {
            acc.withdraw("User B", 700);
        });

        user1.start();
        user2.start();
    }
}
