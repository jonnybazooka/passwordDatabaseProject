package org.sda.database;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sda.database.tables.AddressTable;
import org.sda.database.tables.PasswordsTable;
import org.sda.database.tables.Rot;
import org.sda.database.tables.UserTable;
import org.sda.encryption.Encoder;
import org.sda.exceptions.IllegalDatabaseStateException;
import org.sda.exceptions.PasswordNotFoundException;
import org.sda.exceptions.UserNotFoundException;
import org.sda.user.Address;
import org.sda.user.Country;
import org.sda.user.User;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseManager {

    private static final Logger LOGGER = LogManager.getLogger(DatabaseManager.class.getName());
    private static final Path PATH = Paths.get("D:\\Dokumenty\\Java_Workbench\\PasswordDatabaseProject\\src\\main\\resources\\users.csv");
    private static final Path PASS_PATH = Paths.get("D:\\Dokumenty\\Java_Workbench\\PasswordDatabaseProject\\src\\main\\resources\\passwords.csv");
    private static final String[] HEADERS = {"id", "name", "email", "country", "city", "street", "houseNumber", "hash"};
    private static final String[] PASS_HEADERS = {"ID", "Title", "Desc", "Email", "URL", "Password", "ROT", "key"};
    private static final DatabaseManager INSTANCE = new DatabaseManager();

    private DatabaseManager() {
    }

    public static DatabaseManager getINSTANCE() {
        return INSTANCE;
    }

    List<User> getAllUsers() {
        List<UserTable> userDataList = new ArrayList<>();
        try (
                //InputStream in = getClass().getResourceAsStream(PATH.toString());
                //BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                BufferedReader reader = Files.newBufferedReader(PATH);
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            String[] nextRecord = csvReader.readNext();
            while (nextRecord != null) {
                int id = Integer.parseInt(nextRecord[0]);
                String name = nextRecord[1];
                String email = nextRecord[2];
                String country = nextRecord[3];
                String city = nextRecord[4];
                String street = nextRecord[5];
                int houseNumber = Integer.parseInt(nextRecord[6]);
                String hash = nextRecord[7];
                userDataList.add(new UserTable(id, name, email, Country.valueOf(country), city, street, houseNumber, hash));
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
                //InputStream in = getClass().getResourceAsStream(PATH.toString());
                //BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                BufferedReader reader = Files.newBufferedReader(PATH);
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            String[] nextRecord = csvReader.readNext();
            while (nextRecord != null) {
                int id = Integer.parseInt(nextRecord[0]);
                String name = nextRecord[1];
                String email = nextRecord[2];
                String country = nextRecord[3];
                String city = nextRecord[4];
                String street = nextRecord[5];
                int houseNumber = Integer.parseInt(nextRecord[6]);
                String hash = nextRecord[7];
                UserTable user = new UserTable(id, name, email, Country.valueOf(country), city, street, houseNumber, hash);
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
        throw new UserNotFoundException();
    }

    public User getUserByMail(String userEmail) throws UserNotFoundException {
        try (
                //InputStream in = getClass().getResourceAsStream(PATH.toString());
                //BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                BufferedReader reader = Files.newBufferedReader(PATH);
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            String[] nextRecord = csvReader.readNext();
            while (nextRecord != null) {
                int id = Integer.parseInt(nextRecord[0]);
                String name = nextRecord[1];
                String email = nextRecord[2];
                String country = nextRecord[3];
                String city = nextRecord[4];
                String street = nextRecord[5];
                int houseNumber = Integer.parseInt(nextRecord[6]);
                String hash = nextRecord[7];
                UserTable user = new UserTable(id, name, email, Country.valueOf(country), city, street, houseNumber, hash);
                if (email.equals(userEmail)) {
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
                //InputStream in = getClass().getResourceAsStream(PATH.toString());
                //BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                BufferedReader reader = Files.newBufferedReader(PATH);
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            String[] nextRecord = csvReader.readNext();
            while (nextRecord != null) {
                String country = nextRecord[3];
                String city = nextRecord[4];
                String street = nextRecord[5];
                int houseNumber = Integer.parseInt(nextRecord[6]);
                AddressTable addressTable = new AddressTable(Country.valueOf(country), city, street, houseNumber);
                if (nextRecord[1].equals(userName)) {
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

    public boolean insertUser(UserTable userTable) {
        //String filename = "./classes/users.csv";
        //String filename = "D:/Dokumenty/Java_Workbench/PasswordDatabaseProject/src/main/resources/users.csv";
        try (
                //Writer writer = Files.newBufferedWriter(Paths.get(filename), StandardOpenOption.APPEND);
                Writer writer = Files.newBufferedWriter(PATH, StandardOpenOption.APPEND);
                CSVWriter csvWriter = new CSVWriter(writer)
        ) {
            csvWriter.writeNext(new String[]{nextUniqueId(), userTable.getUserName(), userTable.getEmail()
                    , userTable.getCountry().name(), userTable.getCity(), userTable.getStreet()
                    , String.valueOf(userTable.getHouseNumber()), userTable.getHash()}, false);
            LOGGER.debug("Written to file: " + userTable.getUserName());
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.debug("Failed to add new user.");
            return false;
        }
        LOGGER.debug("New user registered.");
        return true;
    }

    public boolean removeUser(User user) {
        try (
                BufferedReader reader = Files.newBufferedReader(PATH);
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            List<String[]> allRecords = csvReader.readAll();
            Writer writer = Files.newBufferedWriter(PATH, StandardOpenOption.TRUNCATE_EXISTING);
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(HEADERS, false);
            int newID = 1;
            for (String[] line : allRecords) {
                if (line.length != 8) {
                    throw new IllegalDatabaseStateException();
                }
                if (!line[0].equals("")) {
                    int id = Integer.parseInt(line[0]);
                    String name = line[1];
                    String email = line[2];
                    String country = line[3];
                    String city = line[4];
                    String street = line[5];
                    int houseNumber = Integer.parseInt(line[6]);
                    String hash = line[7];
                    UserTable userTable = new UserTable(id, name, email, Country.valueOf(country), city, street, houseNumber, hash);
                    if (!user.getEmail().equals(userTable.getEmail())) {
                        csvWriter.writeNext(new String[]{String.valueOf(newID++), userTable.getUserName(), userTable.getEmail()
                                        , userTable.getCountry().name(), userTable.getCity(), userTable.getStreet()
                                        , String.valueOf(userTable.getHouseNumber()), userTable.getHash()}
                                , false);
                    }
                }
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalDatabaseStateException e) {
            e.getMessage();
        }
        return false;
    }

    public boolean changeAddress(User user, AddressTable addressTable) {
        try (
                BufferedReader reader = Files.newBufferedReader(PATH);
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            List<String[]> allRecords = csvReader.readAll();
            Writer writer = Files.newBufferedWriter(PATH, StandardOpenOption.TRUNCATE_EXISTING);
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(HEADERS, false);
            int newID = 1;
            for (String[] line : allRecords) {
                if (line.length != 8) {
                    throw new IllegalDatabaseStateException();
                }
                if (!line[0].equals("")) {
                    int id = Integer.parseInt(line[0]);
                    String name = line[1];
                    String email = line[2];
                    String country = line[3];
                    String city = line[4];
                    String street = line[5];
                    int houseNumber = Integer.parseInt(line[6]);
                    String hash = line[7];
                    UserTable userTable = new UserTable(id, name, email, Country.valueOf(country), city, street, houseNumber, hash);
                    if (!user.getEmail().equals(userTable.getEmail())) {
                        csvWriter.writeNext(new String[]{String.valueOf(newID++), userTable.getUserName(), userTable.getEmail()
                                        , userTable.getCountry().name(), userTable.getCity(), userTable.getStreet()
                                        , String.valueOf(userTable.getHouseNumber()), userTable.getHash()}
                                , false);
                    } else {
                        csvWriter.writeNext(new String[]{String.valueOf(newID++), userTable.getUserName(), userTable.getEmail()
                                        , addressTable.getCountry().name(), addressTable.getCity(), addressTable.getStreet()
                                        , String.valueOf(addressTable.getHouseNumber()), userTable.getHash()}
                                , false);
                    }
                }
            }
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalDatabaseStateException e) {
            e.getMessage();
        }
        return false;
    }

    private String nextUniqueId() {
        int id = 1;
        try (
                BufferedReader reader = Files.newBufferedReader(PATH);
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            List<String[]> allRecords = csvReader.readAll();
            for (String[] line : allRecords) {
                if (line.length != 8) {
                    throw new IllegalDatabaseStateException();
                }
                if (!line[0].equals("")) {
                    if (line[0].equals(String.valueOf(id))) {
                        id++;
                    } else {
                        return String.valueOf(id);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalDatabaseStateException e) {
            e.getMessage();
        }
        return String.valueOf(id);
    }

    public void showEncryptedPasswords(String key) {
        try (
                BufferedReader reader = Files.newBufferedReader(PASS_PATH);
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            List<String[]> allPasswords = csvReader.readAll();
            for (String[] line : allPasswords) {
                if (line.length != 8) {
                    throw new IllegalDatabaseStateException();
                }
                if (!line[0].equals("")) {
                    if (line[7].equals(key)) {
                        PasswordsTable.PasswordTableBuilder builder = new PasswordsTable.PasswordTableBuilder(Integer.parseInt(line[0]), line[1]
                                , line[5], Rot.valueOf(line[6]), key);
                        if (!line[2].equals(" ")) {
                            builder.setDesc(line[2]);
                        }
                        if (!line[3].equals(" ")) {
                            builder.setEmail(line[3]);
                        }
                        if (!line[4].equals(" ")) {
                            builder.url(line[4]);
                        }
                        PasswordsTable table = builder.build();
                        table.showEncryptedPassword();
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.debug("Passwords read failed.");
        } catch (IllegalDatabaseStateException e) {
            e.getMessage();
        }
    }

    public void showDecryptedPasswords(String key) {
        try (
                BufferedReader reader = Files.newBufferedReader(PASS_PATH);
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            List<String[]> allPasswords = csvReader.readAll();
            for (String[] line : allPasswords) {
                if (line.length != 8) {
                    throw new IllegalDatabaseStateException();
                }
                if (!line[0].equals("")) {
                    if (line[7].equals(key)) {
                        PasswordsTable.PasswordTableBuilder builder = new PasswordsTable.PasswordTableBuilder(Integer.parseInt(line[0]), line[1]
                                , line[5], Rot.valueOf(line[6]), key);
                        if (!line[2].equals(" ")) {
                            builder.setDesc(line[2]);
                        }
                        if (!line[3].equals(" ")) {
                            builder.setEmail(line[3]);
                        }
                        if (!line[4].equals(" ")) {
                            builder.url(line[4]);
                        }
                        PasswordsTable table = builder.build();
                        table.showDecryptedPassword();
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.debug("Passwords read failed.");
        } catch (IllegalDatabaseStateException e) {
            e.getMessage();
        }
    }

    public void addNewPassword(User user, Encoder encoder, String password, String title, String desc, String email, String url) {
        try (
                Writer writer = Files.newBufferedWriter(PASS_PATH, StandardOpenOption.APPEND);
                CSVWriter csvWriter = new CSVWriter(writer)
        ) {
            LOGGER.debug(encoder.getClass().getSimpleName());
            Rot rot;
            if (encoder.getClass().getSimpleName().equals("ROT13")) {
                rot = Rot.ROT13;
            } else if (encoder.getClass().getSimpleName().equals("ROT18")) {
                rot = Rot.ROT18;
            } else {
                rot = Rot.ROT47;
            }
            PasswordsTable.PasswordTableBuilder builder = new PasswordsTable.PasswordTableBuilder(getUniqueID(user)
                    , title, password, rot, user.getEmail());
            if (desc.length() > 0) {
                builder.setDesc(desc);
            }
            if (email.length() > 0) {
                builder.setEmail(email);
            }
            if (url.length() > 0) {
                builder.url(url);
            }
            PasswordsTable table = builder.build();
            csvWriter.writeNext(new String[]{String.valueOf(table.getId()), table.getTitle(), table.getDesc()
                    , table.getEmail(), table.getUrl(), table.getPassword(), table.getRot().name(), table.getKey()}, false);
        } catch (IOException e) {
            LOGGER.debug("Password writing failed.");
        }
    }

    private int getUniqueID(User user) {
        try (
                BufferedReader reader = Files.newBufferedReader(PASS_PATH);
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            int id = 1;
            List<String[]> allPasswords = csvReader.readAll();
            for (String[] line : allPasswords) {
                if (!line[0].equals("")) {
                    if (line[7].equals(user.getEmail())) {
                        if (Integer.parseInt(line[0]) == id) {
                            id++;
                        } else {
                            return id;
                        }
                    }
                }
            }
            return id;
        } catch (IOException e) {
            LOGGER.debug("Unique ID for password couldn't be properly returned.");
        }
        return -1;
    }

    public boolean deletePassword(User user, int number) throws PasswordNotFoundException{
        if(!isPasswordIDValid(user, number)) {
            throw new PasswordNotFoundException();
        }
        try (
                BufferedReader reader = Files.newBufferedReader(PASS_PATH);
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
        ) {
            List<String[]> allPasswords = csvReader.readAll();
            Writer writer = Files.newBufferedWriter(PASS_PATH, StandardOpenOption.TRUNCATE_EXISTING);
            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(PASS_HEADERS, false);
            int count = 1;
            for (String[] line : allPasswords) {
                if (line.length != 8) {
                    throw new IllegalDatabaseStateException();
                }
                if (!line[0].equals("")) {
                    if (!line[7].equals(user.getEmail())) {
                        csvWriter.writeNext(line, false);
                    } else if (!line[0].equals(String.valueOf(number))) {
                        line[0] = String.valueOf(count++);
                        csvWriter.writeNext(line, false);
                    }
                }
            }
            writer.close();
            return true;
        } catch (IOException e) {
            LOGGER.debug("Password removal failed.");
            return false;
        } catch (IllegalDatabaseStateException e) {
            e.getMessage();
            return false;
        }
    }

    private boolean isPasswordIDValid(User user, int number) {
        try (
                BufferedReader reader = Files.newBufferedReader(PASS_PATH);
                CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()
                ){
            List<String[]> allPasswords = csvReader.readAll();
            for (String[] line : allPasswords) {
                if (line.length != 8) {
                    throw new IllegalDatabaseStateException();
                }
                if (!line[0].equals("")) {
                    if (line[7].equals(user.getEmail()) && line[0].equals(String.valueOf(number))) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.debug("Password ID not recognized.");
            return false;
        } catch (IllegalDatabaseStateException e) {
            e.getMessage();
        }
        return false;
    }
}
