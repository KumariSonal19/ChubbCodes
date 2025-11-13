package com.smartpayment.payments;

import com.smartpayment.user.User;
import com.smartpayment.exceptions.*;

public class UPIPayment extends Payment {
    private String upiId;
    private String pin;

    public UPIPayment(User payer, String bene, double amount, String upiId, String pin) {
        super(payer, bene, amount);
        this.upiId = upiId;
        this.pin = pin;
    }

    @Override
    public void processPayment() throws TransactionFailedException {
        try {
            if(amount <= 0)
                throw new InvalidAmountException("Amount must be positive");

            if(amount > payer.getUpiBalance())
                throw new InsufficientBalanceException("Insufficient UPI balance");

            if(pin == null || pin.length() != 4)
                throw new InvalidCredentialsException("Invalid UPI PIN");

            // Deduct amount
            payer.deductUpi(amount);
            System.out.println("UPI Payment successful: " + amount + " to " + bene);

        } catch(Exception e) {
            throw new TransactionFailedException("UPI Payment failed", e);
        }
    }
}


