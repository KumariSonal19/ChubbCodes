package com.app.work;

import java.util.*;
import java.util.stream.*;

class Employee {
    String name;
    double salary;
    String bankName;

    public Employee(String name, double salary, String bankName) {
        this.name = name;
        this.salary = salary;
        this.bankName = bankName;
    }

    public double getSalary() { return salary; }
    public String getBankName() { return bankName; }
}

class Company {
    String name;
    List<Employee> employees;

    public Company(String name, List<Employee> employees) {
        this.name = name;
        this.employees = employees;
    }

    public List<Employee> getEmployees() { return employees; }
}

public class HDFCBankExercise {
    public static void main(String[] args) {
        List<Employee> list = Arrays.asList(
            new Employee("Amit", 50000, "HDFC"),
            new Employee("Sonal", 60000, "SBI"),
            new Employee("Riya", 55000, "HDFC"),
            new Employee("Rahul", 45000, "ICICI")
        );

        Company company = new Company("TechCorp", list);

        double totalPaidByHDFC = company.getEmployees().stream()
            .filter(e -> e.getBankName().equalsIgnoreCase("HDFC"))
            .mapToDouble(Employee::getSalary)
            .sum();

        System.out.println("Total amount paid by HDFC Bank employees: " + totalPaidByHDFC);
    }
}
