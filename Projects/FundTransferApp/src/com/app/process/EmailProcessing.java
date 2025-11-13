package com.app.process;

public interface EmailProcessing {

    boolean validateEmail();

    static boolean sendEmail() {
        System.out.println("Sending emails");
        return true;
    }

    default void intializeEmailServer() {
        System.out.println("Initialize server");
    }
}
