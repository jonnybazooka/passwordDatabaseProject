package org.sda.exceptions;

public class PasswordNotFoundException extends Exception {

    public PasswordNotFoundException() {
        super();
        System.out.println("Password not found.");
    }
}
