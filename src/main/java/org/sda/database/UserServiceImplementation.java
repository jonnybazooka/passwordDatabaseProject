package org.sda.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sda.authentication.authService.AuthService;
import org.sda.authentication.authService.AuthServiceImplementation;
import org.sda.authentication.hashFunction.HashFunction;
import org.sda.authentication.hashFunction.SHA256;
import org.sda.database.tables.AddressTable;
import org.sda.database.tables.UserTable;
import org.sda.encryption.Encoder;
import org.sda.encryption.ROT13;
import org.sda.encryption.ROT18;
import org.sda.encryption.ROT47;
import org.sda.exceptions.DatabaseIntegrityException;
import org.sda.exceptions.PasswordNotFoundException;
import org.sda.exceptions.UserNotFoundException;
import org.sda.user.Country;
import org.sda.user.User;
import org.sda.validation.Validators;

import java.io.Console;
import java.util.List;
import java.util.Scanner;

public class UserServiceImplementation implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImplementation.class.getName());

    @Override
    public List<User> findAllUsers() {
        return DatabaseManager.getINSTANCE().getAllUsers();
    }

    @Override
    public User findUserByName(String userName) throws UserNotFoundException {
        return DatabaseManager.getINSTANCE().getUser(userName);
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        return DatabaseManager.getINSTANCE().getUserByMail(email);
    }

    @Override
    public boolean createUser() throws DatabaseIntegrityException {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();
        Validators validators = new Validators();
        try {
            String name = enterName(scanner, validators);
            String email = enterEmail(scanner, validators);
            System.out.println("|    Enter country:");
            Country country = enterCountry(scanner);
            System.out.println("|    Enter city:");
            String city = scanner.nextLine();
            System.out.println("|    Enter street:");
            String street = scanner.nextLine();
            System.out.println("|    Enter houseNumber:");
            int houseNumber = scanner.nextInt();
            scanner.nextLine();
            String hash = enterPassword(console, validators);
            UserTable userTable = new UserTable(1, name, email, country, city, street, houseNumber, hash);
            DatabaseManager.getINSTANCE().insertUser(userTable);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Country enterCountry(Scanner scanner) {
        System.out.println("|    Enter country:");
        String country = scanner.nextLine();
        return Country.valueOf(country);
    }

    @Override
    public boolean removeUser(User user) throws DatabaseIntegrityException {
        AuthService authService = new AuthServiceImplementation();
        Console console = System.console();
        System.out.println("|    Enter your password to confirm.");
        String password = new String(console.readPassword());
        if (authService.isAuthenticated(user, new SHA256(), password)) {
            DatabaseManager.getINSTANCE().removeUser(user);
            System.out.println("User successfully removed.");
            LOGGER.debug("User " + user.getName() + " removed.");
            return true;
        }
        return false;
    }

    @Override
    public boolean editUsersAddress(User user) throws DatabaseIntegrityException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("|    Enter new country: ");
            Country country = enterCountry(scanner);
            System.out.println("|    Enter new city: ");
            String city = scanner.nextLine();
            System.out.println("|    Enter new street: ");
            String street = scanner.nextLine();
            System.out.println("|    Enter new house number: ");
            int houseNumber = scanner.nextInt();
            AddressTable addressTable = new AddressTable(country, city, street, houseNumber);
            DatabaseManager.getINSTANCE().changeAddress(user, addressTable);
            return true;
        } catch (Exception e) {
            throw new DatabaseIntegrityException();
        }
    }

    @Override
    public boolean showUsersPasswords(User user) throws PasswordNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("|    Do you want to see your passwords encrypted or decrypted?");
        System.out.println("|    1 - Encrypted");
        System.out.println("|    2 - Decrypted");
        int command = scanner.nextInt();
        switch (command) {
            case 1:
                DatabaseManager.getINSTANCE().showEncryptedPasswords(user.getEmail());
                return true;
            case 2:
                DatabaseManager.getINSTANCE().showDecryptedPasswords(user.getEmail());
                return true;
            default:
                throw new PasswordNotFoundException();
        }
    }

    @Override
    public boolean addNewPassword(User user) {
        Console console = System.console();
        Validators validators = new Validators();
        Scanner scanner = new Scanner(System.in);
        System.out.println("|    Choose encryption method:");
        System.out.println("|    1 - ROT13");
        System.out.println("|    2 - ROT17");
        System.out.println("|    3 - ROT47");
        int rotChoice = scanner.nextInt();
        Encoder encoder;
        switch (rotChoice) {
            case 1:
                encoder = new ROT13();
                break;
            case 2:
                encoder = new ROT18();
                break;
            case 3:
                encoder = new ROT47();
                break;
            default:
                System.out.println("Encoder set to default ROT47.");
                encoder = new ROT47();
                break;
        }
        scanner.nextLine();
        String password = enterPassword(console, validators, encoder);
        System.out.println("|    Enter title (mandatory):");
        String title = scanner.nextLine();
        System.out.println("|    Enter description:");
        String desc = scanner.nextLine();
        System.out.println("|    Enter email:");
        String email = scanner.nextLine();
        System.out.println("|    Enter URL:");
        String url = scanner.nextLine();
        DatabaseManager.getINSTANCE().addNewPassword(user, encoder, password, title, desc, email, url);
        return true;
    }

    @Override
    public void editPassword(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("|    Enter password's ID number to edit:");
        int number = scanner.nextInt();
        scanner.nextLine();
        try {
            if(DatabaseManager.getINSTANCE().deletePassword(user, number)) {
                addNewPassword(user);
            } else {
                System.out.println("Password edition failed.");
            }
        } catch (PasswordNotFoundException e) {
            e.getMessage();
        }
    }

    @Override
    public void deletePassword(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("|    Enter password's ID number:");
        int number = scanner.nextInt();
        scanner.nextLine();
        try {
            if (DatabaseManager.getINSTANCE().deletePassword(user, number)) {
                System.out.println("Password deleted successfully.");
            } else {
                System.out.println("Password removal failed.");
            }
        } catch (PasswordNotFoundException e) {
            e.getMessage();
        }
    }

    private String enterPassword(Console console, Validators validators) {
        HashFunction hashFunction = new SHA256();
        String password = " ";
        while (!validators.validatePassword(password)) {
            password = new String(console.readPassword("Enter new password: "));
        }
        return hashFunction.hash(password);
    }

    private String enterPassword(Console console, Validators validators, Encoder encoder) {
        String password = " ";
        while (!validators.validatePassword(password)) {
            password = new String(console.readPassword("Enter new password: "));
        }
        return new String(encoder.encrypt(password.toCharArray()));
    }

    private String enterEmail(Scanner scanner, Validators validators) throws DatabaseIntegrityException {
        String email = " ";
        while (!validators.validateEmail(email)) {
            System.out.println("|    Enter new user's email: ");
            email = scanner.nextLine();
            try {
                if (DatabaseManager.getINSTANCE().getUserByMail(email) != null) {
                    throw new DatabaseIntegrityException();
                }
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
        }
        return email;
    }

    private String enterName(Scanner scanner, Validators validators) {
        String name = " ";
        while (!validators.validateUserName(name)) {
            System.out.println("|    Enter new user's name: ");
            name = scanner.nextLine();
        }
        return name;
    }
}
