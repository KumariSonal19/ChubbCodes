package com.smartpayment.payments;

import com.smartpayment.user.User;
import com.smartpayment.exceptions.*;

public class WalletPayment extends Payment {

    public WalletPayment(User payer, String bene, double amount) {
        super(payer, bene, amount);
    }

    @Override
    public void processPayment() throws TransactionFailedException {
        try {
            if(amount <= 0)
                throw new InvalidAmountException("Amount must be positive");

            if(amount > payer.getWalletBalance())
                throw new InsufficientBalanceException("Insufficient wallet balance");

            payer.deductWallet(amount);
            System.out.println("Wallet Payment successful: " + amount + " to " + bene);

        } catch(Exception e) {
            throw new TransactionFailedException("Wallet Payment failed", e);
        }
    }
}
