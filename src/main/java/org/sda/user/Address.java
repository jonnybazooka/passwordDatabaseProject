package org.sda.user;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Address {
    private String country;
    private String city;
    private String street;
    private int houseNumber;

    public Address() {
    }

    public Address(String country, String city, String street, int houseNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public void changeAddress() {
        Scanner scanner = new Scanner(System.in);
        boolean isAddressChanged = false;
        while (!isAddressChanged) {
            try {
                System.out.println("|    Enter new country: ");
                this.country = scanner.nextLine();
                System.out.println("|    Enter new city: ");
                this.city = scanner.nextLine();
                System.out.println("|    Enter new street: ");
                this.street = scanner.nextLine();
                System.out.println("|    Enter new house number: ");
                this.houseNumber = scanner.nextInt();
                isAddressChanged = true;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("|    Input mismatch. Try again.");
            }
        }

    }

    @Override
    public String toString() {
        return "Country: " + country + "\nCity: " + city + "\nStreet: " + street + "\nHouse Number: " + houseNumber;
    }
}
