package com.app.work;
import java.util.*;
import java.util.stream.*;

class Account {
    private String accountNo;
    private double balance;

    public Account(String accountNo, double balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return accountNo + " : " + balance;
    }
}

public class SortingInStream {
    public static void main(String[] args) {
        //  Create a list of accounts
        List<Account> acctList = Arrays.asList(
            new Account("A101", 1500.0),
            new Account("A102", 3500.0),
            new Account("A103", 2500.0),
            new Account("A104", 1000.0)
        );

        //  Sort based on account number
        var sortedList = acctList.stream()
                .sorted(Comparator.comparing(Account::getAccountNo))
                .collect(Collectors.toList());

        sortedList.forEach(a -> System.out.println("Sorted by Account No: " + a));

        //  Sort based on balance
        acctList.stream()
                .sorted(Comparator.comparingDouble(Account::getBalance))
                .forEach(a -> System.out.println("Sorted by Balance: " + a));

        //  Filter accounts with balance > 2000
        Stream<Account> sortStream = acctList.stream();
        sortStream
                .filter(a -> a.getBalance() > 2000)
                .forEach(a -> System.out.println("Filtered (balance > 2000): " + a));

        //  Sorting a list of integers
        Stream<Integer> strmInt = Stream.of(1, 2, 3, 4, 123, 67, 34, 120);
        strmInt.sorted()
               .forEach(a -> System.out.println("Sorted values: " + a));

        //  Create a Map (key = Account object, value = AccountNo)
        Map<Account, String> accountMap = acctList.stream()
                .collect(Collectors.toMap(
                        a -> a,               // key: Account object
                        Account::getAccountNo // value: account number
                ));

        System.out.println("Map of Account -> AccountNo: " + accountMap);
    }
}
