package org.sda.exceptions;

public class IllegalDatabaseStateException extends Exception {
    public IllegalDatabaseStateException() {
        super();
        System.out.println("Database corrupted. Not enough columns in database.");
    }
}
