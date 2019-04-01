package org.sda;

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
        return "User{" +
                ", name='" + name + '\'' +
                ", passwords=" + passwords +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return  Objects.equals(name, user.name) &&
                Objects.equals(passwords, user.passwords);
    }


}
