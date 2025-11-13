package com.app.salary;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class SalaryProcessorConcurrent {

    public static void main(String[] args) {
        
        Account company = new Account("C001", "CompanyAccount", 100000);

        
        List<Account> employees = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            employees.add(new Account("E00" + i, "Employee-" + i, 0));
        }

        // Create thread pool with 5 threads
        ExecutorService executor = Executors.newFixedThreadPool(5);

        List<Future<Transaction>> futureTransactions = new ArrayList<>();
        Random random = new Random();

       //Submit 30 transfer tasks
        for (int i = 0; i < 30; i++) {
            Account emp = employees.get(random.nextInt(employees.size()));
            double amount = 2000 + random.nextInt(8000); // Random between 2k - 10k

            SalaryTransferTask task = new SalaryTransferTask(company, emp, amount);
            Future<Transaction> result = executor.submit(task);
            futureTransactions.add(result);
        }

        // Collect results
        List<Transaction> transactions = new ArrayList<>();
        for (Future<Transaction> f : futureTransactions) {
            try {
                transactions.add(f.get()); // waits for each transaction to complete
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        // Write transactions to file
        writeToFile(transactions);

        
        System.out.println("\n===== Salary Processing Summary =====");
        transactions.forEach(System.out::println);
        System.out.println("\nRemaining Company Balance: " + company.getBalance());
    }

    private static void writeToFile(List<Transaction> transactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt"))) {
            for (Transaction t : transactions) {
                writer.write(t.toString());
                writer.newLine();
            }
            System.out.println("Transaction details written to transactions.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
