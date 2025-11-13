package com.app.salary;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class SalaryProcessorApp {
    // where data/transactions.txt is located
    private static final String TRANSACTION_FILE = "data/transactions.txt";
    private static final String SUCCESS_LOG = "data/processed_success.txt";
    private static final String FAILED_LOG = "data/processed_failed.txt";

    public static void main(String[] args) {
        // 1) Setup accounts (company + employee accounts).
        Map<String, Account> accounts = new ConcurrentHashMap<>();

        // Company account with big balance
        accounts.put("COMPANY", new Account("COMPANY", 200_000.00));

        // Create employee accounts EMP001..EMP032 with zero initial balance
        for (int i = 1; i <= 40; i++) { // create more if needed
            String empId = String.format("EMP%03d", i);
            accounts.put(empId, new Account(empId, 0.0));
        }

        // 2) Read transactions file into List<Transaction>
        List<Transaction> txns = readTransactions(TRANSACTION_FILE);
        if (txns.isEmpty()) {
            System.out.println("No transactions found in " + TRANSACTION_FILE);
            return;
        }

        // 3) Prepare writers for logs
        try (BufferedWriter successWriter = new BufferedWriter(new FileWriter(SUCCESS_LOG));
             BufferedWriter failedWriter = new BufferedWriter(new FileWriter(FAILED_LOG))) {

            // 4) Thread pool: sized to CPU cores or fixed like 5
            int poolSize = Math.min(8, Runtime.getRuntime().availableProcessors());
            ExecutorService pool = Executors.newFixedThreadPool(poolSize);

            // 5) Submit tasks
            for (Transaction txn : txns) {
                TransactionProcessor processor = new TransactionProcessor(txn, accounts, successWriter, failedWriter);
                pool.submit(processor);
            }

            // 6) Shutdown pool and wait
            pool.shutdown();
            boolean finished = pool.awaitTermination(60, TimeUnit.SECONDS);
            if (!finished) {
                System.out.println("Timeout waiting for tasks to finish. Forcing shutdown.");
                pool.shutdownNow();
            }

            // 7) After all processed, print summary
            System.out.println("Processing complete.");
            System.out.println("Company final balance: " + accounts.get("COMPANY").getBalance());

            // Optionally print all employee balances
            System.out.println("Sample employee balances (non-zero):");
            accounts.entrySet().stream()
                    .filter(e -> e.getKey().startsWith("EMP"))
                    .filter(e -> e.getValue().getBalance() > 0.0)
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> System.out.println(e.getKey() + " = " + e.getValue().getBalance()));

            System.out.println("Success log: " + SUCCESS_LOG);
            System.out.println("Failed log: " + FAILED_LOG);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<Transaction> readTransactions(String pathStr) {
        List<Transaction> txns = new ArrayList<>();
        Path path = Paths.get(pathStr);
        if (!Files.exists(path)) {
            System.err.println("Transaction file not found: " + pathStr);
            return txns;
        }
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                // expected format: txnId,fromAccountId,toAccountId,amount,description
                String[] parts = line.split(",", 5);
                if (parts.length < 5) {
                    System.err.println("Skipping malformed line: " + line);
                    continue;
                }
                String txnId = parts[0].trim();
                String from = parts[1].trim();
                String to = parts[2].trim();
                double amount;
                try {
                    amount = Double.parseDouble(parts[3].trim());
                } catch (NumberFormatException nfe) {
                    System.err.println("Skipping line with bad amount: " + line);
                    continue;
                }
                String desc = parts[4].trim();
                txns.add(new Transaction(txnId, from, to, amount, desc));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return txns;
    }
}
