package com.app.salary;

import java.util.concurrent.Callable;

public class SalaryTransferTask implements Callable<Transaction> {
    private final Account company;
    private final Account employee;
    private final double amount;

    public SalaryTransferTask(Account company, Account employee, double amount) {
        this.company = company;
        this.employee = employee;
        this.amount = amount;
    }

    @Override
    public Transaction call() throws Exception {
        boolean success = company.transfer(employee, amount);
        String status = success ? "SUCCESS" : "FAILED";
        return new Transaction(company.getName(), employee.getName(), amount, status);
    }
}
