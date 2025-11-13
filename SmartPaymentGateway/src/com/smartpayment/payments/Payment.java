package com.smartpayment.payments;

import com.smartpayment.user.User;
import com.smartpayment.exceptions.*;

public abstract class Payment {
    protected User payer;
    protected String bene;
    protected double amount;

    public Payment(User payer, String bene, double amount) {
        this.payer = payer;
        this.bene = bene;
        this.amount = amount;
    }

    public String getBene() { return bene; }
    public double getAmount() { return amount; }

    public abstract void processPayment() throws TransactionFailedException;
}
