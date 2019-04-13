package org.sda.database;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.sda.database.tables.AddressTable;
import org.sda.database.tables.UserTable;
import org.sda.exceptions.UserNotFoundException;
import org.sda.user.Address;
import org.sda.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseManager {

    private static final String USERS_CSV_FILE_PATH = "/users.csv";

    private static final DatabaseManager INSTANCE = new DatabaseManager();

    private DatabaseManager() {
    }

    public static DatabaseManager getINSTANCE() {
        return INSTANCE;
    }

    List<User> getAllUsers() {
        List<UserTable> userDataList = new ArrayList<>();
        try (
            InputStream in = getClass().getResourceAsStream(USERS_CSV_FILE_PATH);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            String[] nextRecord = csvReader.readNext();
            while (nextRecord != null) {
                String name = nextRecord[0];
                String email = nextRecord[1];
                String country = nextRecord[2];
                String city = nextRecord[3];
                String street = nextRecord[4];
                int houseNumber = Integer.parseInt(nextRecord[5]);
                String hash = nextRecord[6];
                userDataList.add(new UserTable(name, email, country, city, street, houseNumber, hash));
                nextRecord = csvReader.readNext();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<User> users = new ArrayList<>();
        for (UserTable user : userDataList) {
            users.add(new User(new Address(user.getCountry(), user.getCity(), user.getStreet(), user.getHouseNumber())
                    , user.getUserName(), user.getEmail(), user.getHash()));
        }
        return users;
    }

    public User getUser(String userName) throws UserNotFoundException {
        try (
                InputStream in = getClass().getResourceAsStream(USERS_CSV_FILE_PATH);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            String[] nextRecord = csvReader.readNext();
            while (nextRecord != null) {
                String name = nextRecord[0];
                String email = nextRecord[1];
                String country = nextRecord[2];
                String city = nextRecord[3];
                String street = nextRecord[4];
                int houseNumber = Integer.parseInt(nextRecord[5]);
                String hash = nextRecord[6];
                UserTable user = new UserTable(name, email, country, city, street, houseNumber, hash);
                if (userName.equals(name)) {
                    return new User(new Address(user.getCountry(), user.getCity(), user.getStreet(), user.getHouseNumber())
                            , user.getUserName(), user.getEmail(), user.getHash());
                } else {
                    nextRecord = csvReader.readNext();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Address getAddress(String userName) {
        try (
                InputStream in = getClass().getResourceAsStream(USERS_CSV_FILE_PATH);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
                ){
            String[] nextRecord = csvReader.readNext();
            while (nextRecord != null) {
                String country = nextRecord[2];
                String city = nextRecord[3];
                String street = nextRecord[4];
                int houseNumber = Integer.parseInt(nextRecord[5]);
                AddressTable addressTable = new AddressTable(country, city, street, houseNumber);
                if (nextRecord[0].equals(userName)) {
                    return new Address(addressTable.getCountry(), addressTable.getCity(), addressTable.getStreet()
                            , addressTable.getHouseNumber());
                } else {
                    nextRecord = csvReader.readNext();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
