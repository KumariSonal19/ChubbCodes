package com.example;

public class MathOperation {

    public int add(int a, int b) {
        return a + b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }
    
    public boolean validateName(String custname) {
        if (custname == null) return false;
        return custname.equals("James");
    }
}
