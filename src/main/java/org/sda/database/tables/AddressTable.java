package org.sda.database.tables;

import org.sda.user.Country;

public class AddressTable {
    private Country country;
    private String city;
    private String street;
    private int houseNumber;

    public AddressTable(Country country, String city, String street, int houseNumber) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = Country.valueOf(country);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }
}
