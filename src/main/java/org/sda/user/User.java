package org.sda.user;

import org.sda.authentication.hashFunction.HashFunction;
import org.sda.authentication.hashFunction.SHA256;
import org.sda.validation.Validators;

import java.io.Console;
import java.util.Objects;

public class User implements Comparable<User>{

    private Address address;
    private String name;
    private String scienceDegree;
    private String email;
    private String password;

    public User(Address address, String name, String email, String password) {
        this.address = address;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScienceDegree() {
        return scienceDegree;
    }

    public void setScienceDegree(String scienceDegree) {
        this.scienceDegree = scienceDegree;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        HashFunction hashFunction = new SHA256();
        Console console = System.console();
        Validators validators = new Validators();
        String password1 = new String(console.readPassword("Enter new password: "));
        String password2 = new String(console.readPassword("Confirm new password: "));
        if (validators.doPasswordsMatch(password1, password2) && validators.validatePassword(password1)
                && validators.validatePassword(password2)) {
            password = hashFunction.hash(password1);
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

    @Override
    public int compareTo(User o) {
        return this.name.compareTo(o.getName());
    }
}
