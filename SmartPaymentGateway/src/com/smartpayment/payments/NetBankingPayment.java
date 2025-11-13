package com.smartpayment.payments;

import com.smartpayment.user.User;
import com.smartpayment.exceptions.*;

public class NetBankingPayment extends Payment {

    public NetBankingPayment(User payer, String bene, double amount) {
        super(payer, bene, amount);
    }

    @Override
    public void processPayment() throws TransactionFailedException {
        try {
            if(amount <= 0)
                throw new InvalidAmountException("Amount must be positive");

            // For simplicity, assume NetBanking has same balance as CreditCard
            if(amount > payer.getCreditLimit())
                throw new InsufficientBalanceException("Insufficient NetBanking balance");

            payer.deductCredit(amount); // using creditLimit
            System.out.println("NetBanking Payment successful: " + amount + " to " + bene);

        } catch(Exception e) {
            throw new TransactionFailedException("NetBanking Payment failed", e);
        }
    }
}
