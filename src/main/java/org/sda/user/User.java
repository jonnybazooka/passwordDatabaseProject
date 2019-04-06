package org.sda.user;

import org.sda.validation.Validators;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private Address address;
    private String name;
    private List<char[]> passwords;

    public User(Address address, String name) {
        this.address = address;
        this.name = name;
        this.passwords = new ArrayList<>();
    }

    public void addPassword(char[] password) {
        passwords.add(password);
    }

    public String getName() {
        return name;
    }

    public List<char[]> getPasswords() {
        return passwords;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" + address.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  Objects.equals(name, user.name);
    }


    public boolean changePassword() {
        Console console = System.console();
        Validators validators = new Validators();
        String password1 = new String(console.readPassword("Enter new password: "));
        String password2 = new String(console.readPassword("Confirm new password: "));
        if (validators.doPasswordsMatch(password1, password2) && validators.validatePassword(password1)
                && validators.validatePassword(password2)) {
            passwords.set(0, password1.toCharArray());
            System.out.println("Password changed successfully.");
            return true;
        } else if (!validators.validatePassword(password1) || !validators.validatePassword(password2)) {
            System.out.println("Password must be 7-15 characters long, and contain at least 2 upper case characters, and 2 digits.");
            return false;
        } else {
            System.out.println("Passwords must match.");
            return false;
        }
    }
}
