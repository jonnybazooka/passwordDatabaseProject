package org.sda;

public class Address {
    private String country;
    private String city;
    private String street;
    private int houseNumber;

    public Address(String country, String city, String street, int houseNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", housenumber=" + houseNumber +
                '}';
    }
}
