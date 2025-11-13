package com.app.main;

import com.app.dto.Account;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PaymentProcessor {

   
    private static final String INPUT_FILE = "C:/Users/KIIT01/Documents/chubb/inputFile.txt";

    public static void main(String[] args) {
        PaymentProcessor p = new PaymentProcessor();
        p.processTransfers(INPUT_FILE);
    }

    public void processTransfers(String filePath) {
        double totalPaidByHDFC = 0.0;
        int lineNo = 0;

        // store all account details
        Map<String, Account> accountMap = new HashMap<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNo++;
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();

                if (parts.length < 11) {
                    System.out.printf("Line %d: invalid format (expected 11 fields). Skipping.%n", lineNo);
                    continue;
                }

                try {
                    // Sender
                    String senderName = parts[0];
                    String senderCountry = parts[1];
                    String senderAccountNo = parts[2];
                    String senderIFSC = parts[3];
                    double senderBalance = Double.parseDouble(parts[4]);

                    // Transfer
                    double transferAmount = Double.parseDouble(parts[5]);
                    String transCode = parts[6];

                    // Receiver
                    String receiverName = parts[7];
                    String receiverCountry = parts[8];
                    String receiverAccountNo = parts[9];
                    String receiverIFSC = parts[10];

                    Account sender = new Account(senderName, senderAccountNo, transCode,
                            senderCountry, senderIFSC, senderBalance);
                    Account receiver = new Account(receiverName, receiverAccountNo,
                            receiverCountry, receiverIFSC);

                    // validation
                    if (transferAmount <= 0) {
                        System.out.printf("Line %d: Invalid amount (<=0). Skipping.%n", lineNo);
                        continue;
                    }

                    if (sender.getBalance() < transferAmount) {
                        System.out.printf("Line %d: Insufficient funds for %s. Skipping.%n",
                                lineNo, sender.getAccountHolderName());
                        continue;
                    }

                    // transfer
                    sender.debit(transferAmount);
                    Account storedReceiver = accountMap.get(receiver.getAccountNo());
                    if (storedReceiver == null) {
                        receiver.setBalance(transferAmount);
                        accountMap.put(receiver.getAccountNo(), receiver);
                    } else {
                        storedReceiver.credit(transferAmount);
                    }

                    accountMap.put(sender.getAccountNo(), sender);

                    System.out.printf("Line %d: SUCCESS - %.2f transferred from %s to %s%n",
                            lineNo, transferAmount, senderName, receiverName);

                    // Total paid by HDFC
                    if (senderIFSC.toUpperCase().contains("HDFC")) {
                        totalPaidByHDFC += transferAmount;
                    }

                } catch (NumberFormatException nfe) {
                    System.out.printf("Line %d: Number format error: %s%n", lineNo, nfe.getMessage());
                } catch (Exception ex) {
                    System.out.printf("Line %d: Error: %s%n", lineNo, ex.getMessage());
                }
            }

            System.out.println("\n--- SUMMARY ---");
            System.out.printf("Total amount paid by HDFC bank: %.2f%n", totalPaidByHDFC);
            System.out.println("\nFinal Balances:");
            accountMap.values().forEach(System.out::println);

        } catch (NoSuchFileException nsfe) {
            System.err.printf("❌ File not found: %s%n", filePath);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
