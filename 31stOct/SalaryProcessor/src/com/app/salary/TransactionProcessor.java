package com.app.salary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class TransactionProcessor implements Runnable {
    private final Transaction txn;
    private final Map<String, Account> accountsMap;
    private final BufferedWriter successWriter;
    private final BufferedWriter failedWriter;

    public TransactionProcessor(Transaction txn, Map<String, Account> accountsMap,
                                BufferedWriter successWriter, BufferedWriter failedWriter) {
        this.txn = txn;
        this.accountsMap = accountsMap;
        this.successWriter = successWriter;
        this.failedWriter = failedWriter;
    }

    @Override
    public void run() {
        String fromId = txn.getFromAccountId();
        String toId = txn.getToAccountId();
        double amount = txn.getAmount();

        Account from = accountsMap.get(fromId);
        Account to = accountsMap.get(toId);

        if (from == null || to == null) {
            writeFailed("UNKNOWN_ACCOUNT," + txn + ", reason=missing_account");
            return;
        }

        // Simple approach: withdraw from 'from', then deposit to 'to'.
        // Withdrawal and deposit are synchronized in Account methods.
        boolean withdrawn = from.withdraw(amount);
        if (!withdrawn) {
            writeFailed("INSUFFICIENT_FUNDS," + txn + ", companyBalance=" + from.getBalance());
            return;
        }

        // deposit
        to.deposit(amount);

        // log success
        writeSuccess("SUCCESS," + txn + ", companyBalanceAfter=" + from.getBalance());
    }

    private synchronized void writeSuccess(String line) {
        try {
            successWriter.write(line);
            successWriter.newLine();
            successWriter.flush();
        } catch (IOException e) {
            System.err.println("Failed to write success log: " + e.getMessage());
        }
    }

    private synchronized void writeFailed(String line) {
        try {
            failedWriter.write(line);
            failedWriter.newLine();
            failedWriter.flush();
        } catch (IOException e) {
            System.err.println("Failed to write failed log: " + e.getMessage());
        }
    }
}
