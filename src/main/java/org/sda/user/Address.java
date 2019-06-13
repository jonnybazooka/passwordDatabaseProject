package org.sda.user;

public class Address {
    private Country country;
    private String city;
    private String street;
    private int houseNumber;

    public Address(Country country, String city, String street, int houseNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getCountry() {
        return country.name();
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    @Override
    public String toString() {
        return "Country: " + country.name() + "\nCity: " + city + "\nStreet: " + street + "\nHouse Number: " + houseNumber;
    }
}
