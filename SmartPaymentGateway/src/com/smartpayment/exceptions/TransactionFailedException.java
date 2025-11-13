package com.smartpayment.exceptions;

public class TransactionFailedException extends Exception {
    public TransactionFailedException(String msg, Exception e) {
        super(msg, e);
    }
}
