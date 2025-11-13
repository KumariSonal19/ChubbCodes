package com.app.salary;

public class Employee {
    private final String id;
    private final String name;
    private final String accountId;

    public Employee(String id, String name, String accountId) {
        this.id = id;
        this.name = name;
        this.accountId = accountId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getAccountId() { return accountId; }

    @Override
    public String toString() {
        return "Employee{" + id + ", " + name + ", acct=" + accountId + "}";
    }
}
