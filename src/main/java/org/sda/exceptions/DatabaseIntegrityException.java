package org.sda.exceptions;

public class DatabaseIntegrityException extends Exception{

    public DatabaseIntegrityException() {
        super();
        System.out.println("Key already exists in database. Keys must be unique.");
    }
}
