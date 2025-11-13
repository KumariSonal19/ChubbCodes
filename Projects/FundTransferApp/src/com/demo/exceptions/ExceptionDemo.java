package com.demo.exceptions;

import java.io.*;

// Main class
public class ExceptionDemo {

    public static void main(String[] args) {

        System.out.println("=== Exception Demo Start ===\n");

        demoTryCatch();
        demoMultipleCatch();
        demoDivideByZero();
        demoArrayIndexOutOfBounds();
        demoFileNotFound();
        demoIOException();
        demoCustomCheckedException();
        demoCustomRuntimeException();
        demoThrowAndThrows();

        System.out.println("\n=== Exception Demo Finished ===");
    }

    // 1) Simple try-catch
    public static void demoTryCatch() {
        System.out.println("1) demoTryCatch:");
        try {
            int x = Integer.parseInt("abc");
            System.out.println("Parsed: " + x);
        } catch (NumberFormatException e) {
            System.out.println("Caught NumberFormatException: " + e.getMessage());
        }
        System.out.println();
    }

    // 2) Try with multiple catch blocks
    public static void demoMultipleCatch() {
        System.out.println("2) demoMultipleCatch:");
        try {
            String s = null;
            System.out.println(s.length()); // NullPointerException
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Generic Exception caught");
        }
        System.out.println();
    }

    // 3) Divide by zero -> ArithmeticException
    public static void demoDivideByZero() {
        System.out.println("3) demoDivideByZero:");
        try {
            int a = 10;
            int b = 0;
            int c = a / b;
            System.out.println("Result: " + c);
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException (divide by zero): " + e.getMessage());
        }
        System.out.println("");
    }

    // 4) ArrayIndexOutOfBoundsException
    public static void demoArrayIndexOutOfBounds() {
        System.out.println("4) demoArrayIndexOutOfBounds:");
        try {
            int[] arr = {1, 2, 3};
            System.out.println(arr[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught ArrayIndexOutOfBoundsException: " + e.getMessage());
        }
        System.out.println();
    }

    // 5) FileNotFoundException
    public static void demoFileNotFound() {
        System.out.println("5) demoFileNotFound:");
        try {
            FileReader fr = new FileReader("nonexistent.txt");
            fr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        }
        System.out.println();
    }

    // 6) IOException using try-with-resources
    public static void demoIOException() {
        System.out.println("6) demoIOException:");
        try (BufferedReader br = new BufferedReader(new FileReader("also_missing.txt"))) {
            String line = br.readLine();
            System.out.println(line);
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
        System.out.println();
    }

    // 7) Custom checked exception
    public static void demoCustomCheckedException() {
        System.out.println("7) demoCustomCheckedException:");
        try {
            throwChecked(true);
        } catch (MyCheckedException e) {
            System.out.println("Caught MyCheckedException: " + e.getMessage());
        }
        System.out.println();
    }

    public static void throwChecked(boolean doThrow) throws MyCheckedException {
        if (doThrow) throw new MyCheckedException("Custom checked exception occurred");
        System.out.println("No checked exception thrown");
    }

    // 8) Custom runtime exception
    public static void demoCustomRuntimeException() {
        System.out.println("8) demoCustomRuntimeException:");
        try {
            throwRuntime(true);
        } catch (MyRuntimeException e) {
            System.out.println("Caught MyRuntimeException: " + e.getMessage());
        }
        System.out.println();
    }

    public static void throwRuntime(boolean doThrow) {
        if (doThrow) throw new MyRuntimeException("Custom runtime exception occurred");
        System.out.println("No runtime exception thrown");
    }

    // 9) throw and throws example
    public static void demoThrowAndThrows() {
        System.out.println("9) demoThrowAndThrows:");
        try {
            methodThatThrows();
        } catch (IOException e) {
            System.out.println("Caught IOException from methodThatThrows: " + e.getMessage());
        }
        System.out.println();
    }

    public static void methodThatThrows() throws IOException {
        throw new IOException("Simulated IOException from methodThatThrows");
    }

    // --- Custom exception classes ---
    // Checked
    public static class MyCheckedException extends Exception {
        public MyCheckedException(String msg) { super(msg); }
    }

    // Runtime
    public static class MyRuntimeException extends RuntimeException {
        public MyRuntimeException(String msg) { super(msg); }
    }
}
