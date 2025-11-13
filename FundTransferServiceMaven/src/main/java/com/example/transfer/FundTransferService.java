package com.example.transfer;

public class FundTransferService {

    private final AccountService accountService;

    public FundTransferService(AccountService accountService) {
        this.accountService = accountService;
    }

    public String transfer(String fromAccount, String toAccount, double amount) {

        //Amount check
        if (amount <= 0) {
            return "FAILURE: Invalid amount";
        }
        
        if (amount > 500000) {
            return "FAILURE: Amount exceeds transfer limit";
        }
        
        //  Same account check
        if (fromAccount.equals(toAccount)) {
            return "FAILURE: Source and destination cannot be same";
        }

        // Check source account balance
        double fromBalance = accountService.getBalance(fromAccount);
        if (fromBalance < amount) {
            return "FAILURE: Insufficient funds";
        }

        // Check if destination account exists
        if (!accountService.exists(toAccount)) {
            return "FAILURE: Destination account not found";
        }

        // Process the transfer
        boolean debitSuccess = accountService.debit(fromAccount, amount);
        boolean creditSuccess = accountService.credit(toAccount, amount);

        // Check transaction success
        if (debitSuccess && creditSuccess) {
            return "SUCCESS: Transfer completed";
        } else {
            return "FAILURE: Transaction error";
        }
    }
}
