package com.app.process;

import java.util.*;
import com.app.dto.Account;

public class AccountOperations {

    public static void main(String[] args) {
        List<Account> accounts = new ArrayList<>();

        accounts.add(new Account("Sonal", "1002", "T001", "India", "SBIN001", 40000));
        accounts.add(new Account("Aashish", "1001", "T002", "India", "HDFC002", 25000));
        accounts.add(new Account("Neha", "1004", "T003", "India", "ICIC003", 80000));
        accounts.add(new Account("Rohan", "1003", "T004", "India", "PNB004", 60000));

        System.out.println("\n--- Sorted by AccountHolderName (Comparable) ---");
        Collections.sort(accounts);
        accounts.forEach(System.out::println);

        System.out.println("\n--- Sorted by AccountNo (Comparator) ---");
        Collections.sort(accounts, new AccountNoComparator());
        accounts.forEach(System.out::println);

        System.out.println("\n--- Sorted by Balance (Comparator) ---");
        Collections.sort(accounts, new BalanceComparator());
        accounts.forEach(System.out::println);

        // Test equals and hashCode
        Account a1 = new Account("Sonal", "1002", "T009", "India", "SBIN009", 50000);
        Account a2 = new Account("sonal", "1002", "T010", "India", "SBIN010", 45000);

        System.out.println("\nAre a1 and a2 same? " + a1.equals(a2));
    }
}
