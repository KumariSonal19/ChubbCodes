package com.smartpayment.payments;

import com.smartpayment.user.User;
import com.smartpayment.exceptions.*;

public class CreditCardPayment extends Payment {

    public CreditCardPayment(User payer, String bene, double amount) {
        super(payer, bene, amount);
    }

    @Override
    public void processPayment() throws TransactionFailedException {
        try {
            if(amount <= 0)
                throw new InvalidAmountException("Amount must be positive");

            if(amount > payer.getCreditLimit())
                throw new InsufficientBalanceException("Credit limit exceeded");

            payer.deductCredit(amount);
            System.out.println("Credit Card Payment successful: " + amount + " to " + bene);

        } catch(Exception e) {
            throw new TransactionFailedException("CreditCard Payment failed", e);
        }
    }
}
