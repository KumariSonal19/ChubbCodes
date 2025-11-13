package com.app.dto;

import com.app.process.ProcessPayment;
import com.app.process.SMSProcessing;

public class NEFTProcessFund extends ProcessPayment implements SMSProcessing {

    public static void processFund(Customer intiator, Customer bene, double amount) throws AccountBalanceException {
        System.out.println("Hi this is first program in NEFTProcessFund");

        if (intiator != null && bene != null) {
            if (intiator.getAmountbalance() > amount && amount < 2000000) {
                double balanceamount = intiator.getAmountbalance() - amount;
                intiator.setAmount(balanceamount);
                bene.setAmount(bene.getAmountbalance() + amount);
                System.out.println("Process fund immediately");
            } else {
                throw new AccountBalanceException("Not having sufficient balance or not a NEFT payment");
            }
        }
    }

    @Override
    public boolean validateCustomer(Customer c1) {
        if (c1.getName() != null && !c1.getName().equals("Bin Laden")) {
            return true;
        }
        return false;
    }

    @Override
    public boolean validateEmail() {
        return false;
    }

    @Override
    public boolean sendSMS(Customer c1) {
        System.out.println("Sent SMS to customer " + c1.getName());
        return true;
    }
}
