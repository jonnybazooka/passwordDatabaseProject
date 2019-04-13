package org.sda;

import org.apache.logging.log4j.Logger;
import org.sda.authentication.authService.AuthService;
import org.sda.authentication.hashFunction.SHA256;
import org.sda.comparators.UserNameAndAdressComparator;
import org.sda.database.DatabaseManager;
import org.sda.database.UserService;
import org.sda.exceptions.UserNotFoundException;
import org.sda.user.Address;
import org.sda.user.User;
import org.sda.validation.Validators;

import java.io.Console;
import java.util.*;

public class Menu {

    private String password;
    private List<User> users;
    private User currentUser;
    private boolean shutdown;
    private boolean isUserLoggedIn;
    private UserService userService;
    private AuthService authService;
    private Logger LOGGER;

    public Menu(UserService userService, AuthService authService, Logger LOGGER) {
        this.password = null;
        this.users = userService.findAllUsers();
        this.shutdown = false;
        this.isUserLoggedIn = false;
        this.currentUser = null;
        this.userService = userService;
        this.authService = authService;
        this.LOGGER = LOGGER;
    }

    public Menu(List<User> users, String name, String password, UserService userService) {
        /*this.users = users;
        this.password = password;
        this.userService = userService;
        if (au) {
            this.currentUser = getUser(name);
            this.isUserLoggedIn = true;
            this.shutdown = false;
        } else {
            System.out.println("User not recognised.");
            this.currentUser = null;
            this.isUserLoggedIn = false;
            this.shutdown = false;
            this.password = null;
        }*/
    }

    public void menuController() {
        Collections.sort(users, new UserNameAndAdressComparator());
        if (isUserLoggedIn) {
            userView();
        } else {
            loginView();
        }
        do {
            if (isUserLoggedIn) {
                userView();
            } else {
                mainMenuView();
            }
        } while (!shutdown);
    }

    private void userView() {
        final int SHOW_USER_PASSWORDS = 1;
        final int CHANGE_MASTER_PASSWORD = 2;
        final int SHOW_USER_ADDRESS = 3;
        final int CHANGE_ADDRESS = 4;
        final int LOG_OUT = 5;
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======================================");
        System.out.println("|    Hello user " + currentUser.getName());
        System.out.println("|    1. Show user stored passwords    |");
        System.out.println("|    2. Change master password        |");
        System.out.println("|    3. Show user address             |");
        System.out.println("|    4. Change address                |");
        System.out.println("|    5. Log out                       |");
        System.out.println("=======================================");
        int digit = scanner.nextInt();
        switch (digit) {
            case SHOW_USER_PASSWORDS:
                System.out.println(currentUser.getPassword());
                break;
            case CHANGE_MASTER_PASSWORD:
                if (currentUser.changePassword()) {
                    isUserLoggedIn = false;
                    currentUser = null;
                }
                break;
            case SHOW_USER_ADDRESS:
                LOGGER.debug("Reading current users address form database.");
                showUsersAddress();
                break;
            case CHANGE_ADDRESS:
                currentUser.getAddress().changeAddress();
                break;
            case LOG_OUT:
                isUserLoggedIn = false;
                currentUser = null;
                LOGGER.debug("User logged out.");
                break;
            default:
                System.out.println("Wrong input, try again");
                break;
        }
    }

    private void mainMenuView() {
        final int LOG_IN = 1;
        final int REGISTER_NEW_USER = 2;
        final int LIST_ALL_USERS = 3;
        final int SHOW_PUBLIC_INFORMATION = 4;
        final int EXIT = 5;
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======================================");
        System.out.println("|    1. Log In                        |");
        System.out.println("|    2. Register new user             |");
        System.out.println("|    3. List all users                |");
        System.out.println("|    4. Show public information       |");
        System.out.println("|    5. Exit                          |");
        System.out.println("=======================================");
        int digit = scanner.nextInt();
        switch (digit) {
            case LOG_IN:
                loginView();
                break;
            case REGISTER_NEW_USER:
                LOGGER.debug("Registering new user.");
                registerNewUser();
                Collections.sort(users, new UserNameAndAdressComparator());
                break;
            case LIST_ALL_USERS:
                listAllUsers();
                break;
            case SHOW_PUBLIC_INFORMATION:
                currentUser = getUser();
                showPublicInformation();
                currentUser = null;
                break;
            case EXIT:
                System.out.println("Goodbye!");
                shutdown = true;
                break;
            default:
                System.out.println("Wrong input.");
                break;
        }
    }

    private void showPublicInformation() {
        if (currentUser != null) {
            System.out.println(currentUser);
        } else {
            System.out.println("User not found.");
        }
    }

    private User getUser() {
        User user = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("|    Enter user name: ");
        String userName = scanner.nextLine();
        try {
            user = userService.findUserByName(userName);
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
        LOGGER.debug("Returning user from database.");
        return user;
    }

    private void registerNewUser() {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();
        Validators validators = new Validators();
        System.out.println("|    Enter new user's name: ");
        String name = scanner.nextLine();
        if (!validators.validateUserName(name)) {
            System.out.println("Name must be 5-15 characters long, and cannot start with a digit.");
        } else {
            try {
                if (userService.findUserByName(name) != null){
                    System.out.println("User name already taken.");
                } else {
                    password = new String(console.readPassword("|    Enter new password for user " + name + " :"));
                    if (!validators.validatePassword(password)) {
                        System.out.println("Password must be 7-15 characters long, and contain at least 2 upper case characters, and 2 digits.");
                    } else {
                        Address address = new Address();
                        address.changeAddress();
                        String email = setEmail();
                        User newUser = new User(address, name, email, new SHA256().hash(password));
                        users.add(newUser);
                        LOGGER.debug("New user added to object users. Not to csv file.");
                        System.out.println("User " + newUser.getName() + " registered successfully.");
                    }
                }
            } catch (UserNotFoundException e) {
                System.out.println("User name already taken.");
            }
        }
        password = null;
    }

    private String setEmail() {
        Scanner scanner = new Scanner(System.in);
        Validators validators = new Validators();
        boolean isValidated = false;
        String email = "";
        while (!isValidated) {
            System.out.println("|    Enter email: ");
            email = scanner.nextLine();
            if(validators.validateEmail(email)) {
                isValidated = true;
            } else {
                System.out.println("Email not valid, try again.");
            }
        }
        return email;
    }

    private void listAllUsers() {
        LOGGER.debug("Listing users form object users.");
        users.stream()
                .map(e -> e.getName())
                .forEach(System.out::println);
    }

    private void loginView() {
        Console console = System.console();
        System.out.println("==============================");
        System.out.println("|        LOGIN SCREEN        |");
        System.out.println("|    Enter user name:     ");
        String userName = console.readLine();
        password = new String(console.readPassword("|    Enter password:    "));
        try {
            if (authService.isAuthenticated(userService.findUserByName(userName), new SHA256(), password)) {
                isUserLoggedIn = true;
                currentUser = userService.findUserByName(userName);
                LOGGER.debug("User logged in.");
            } else {
                System.out.println("User name or password incorrect.");
                LOGGER.debug("User not recognized. Wrong password or name.");
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void showUsersAddress() {
        Address address = DatabaseManager.getINSTANCE().getAddress(currentUser.getName());
        if (address != null) {
            System.out.println(address.toString());
        } else {
            System.out.println("No address found.");
        }
    }
}
