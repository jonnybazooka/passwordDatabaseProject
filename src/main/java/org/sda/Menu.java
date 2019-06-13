package org.sda;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sda.authentication.authService.AuthService;
import org.sda.authentication.hashFunction.SHA256;
import org.sda.comparators.UserNameAndAdressComparator;
import org.sda.database.DatabaseManager;
import org.sda.database.UserService;
import org.sda.exceptions.DatabaseIntegrityException;
import org.sda.exceptions.PasswordNotFoundException;
import org.sda.exceptions.UserNotFoundException;
import org.sda.user.Address;
import org.sda.user.User;
import java.io.Console;
import java.util.*;

public class Menu {

    private static final Logger LOGGER = LogManager.getLogger(Menu.class.getName());

    private String password;
    private User currentUser;
    private boolean shutdown;
    private boolean isUserLoggedIn;
    private UserService userService;
    private AuthService authService;

    public Menu(UserService userService, AuthService authService) {
        this.password = null;
        this.shutdown = false;
        this.isUserLoggedIn = false;
        this.currentUser = null;
        this.userService = userService;
        this.authService = authService;
    }

    public Menu(String name, String password, UserService userService, AuthService authService) {
        this.password = password;
        this.userService = userService;
        this.authService = authService;
        try {
            if (authService.isAuthenticated(userService.findUserByName(name), new SHA256(), password)) {
                this.currentUser = userService.findUserByName(name);
                this.isUserLoggedIn = true;
                this.shutdown = false;
            } else {
                System.out.println("User not recognised.");
                this.currentUser = null;
                this.isUserLoggedIn = false;
                this.shutdown = false;
                this.password = null;
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void menuController() {
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
        final int SHOW_MASTER_PASSWORD = 1;
        final int CHANGE_MASTER_PASSWORD = 2;
        final int SHOW_STORED_PASSWORDS = 3;
        final int ADD_NEW_PASSWORD = 4;
        final int DELETE_PASSWORD = 5;
        final int EDIT_PASSWORD = 6;
        final int SHOW_USER_ADDRESS = 7;
        final int CHANGE_ADDRESS = 8;
        final int REMOVE_USER = 9;
        final int LOG_OUT = 10;
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======================================");
        System.out.println("|    Hello user " + currentUser.getName());
        System.out.println("|    1. Show master hash              |");
        System.out.println("|    2. Change master password        |");
        System.out.println("|    3. Show stored passwords         |");
        System.out.println("|    4. Add new password              |");
        System.out.println("|    5. Delete password               |");
        System.out.println("|    6. Edit password                 |");
        System.out.println("|    7. Show user address             |");
        System.out.println("|    8. Change address                |");
        System.out.println("|    9. Remove user                   |");
        System.out.println("|    10. Log out                      |");
        System.out.println("=======================================");
        int digit = scanner.nextInt();
        switch (digit) {
            case SHOW_MASTER_PASSWORD:
                System.out.println(currentUser.getPassword());
                break;
            case CHANGE_MASTER_PASSWORD:
                if (currentUser.changePassword()) {
                    isUserLoggedIn = false;
                    currentUser = null;
                }
                break;
            case SHOW_STORED_PASSWORDS:
                LOGGER.debug("Showing user's passwords from database.");
                try {
                    userService.showUsersPasswords(currentUser);
                } catch (PasswordNotFoundException e) {
                    LOGGER.debug("Listing passwords for user failed. Passwords not found.");
                } catch (UserNotFoundException e) {
                    LOGGER.debug("Listing passwords for user failed. User not found.");
                }
                break;
            case ADD_NEW_PASSWORD:
                LOGGER.debug("Adding new password to database.");
                userService.addNewPassword(currentUser);
                break;
            case DELETE_PASSWORD:
                userService.deletePassword(currentUser);
                break;
            case EDIT_PASSWORD:
                userService.editPassword(currentUser);
                break;
            case SHOW_USER_ADDRESS:
                LOGGER.debug("Reading current users address form database.");
                showUsersAddress();
                break;
            case CHANGE_ADDRESS:
                try {
                    userService.editUsersAddress(currentUser);
                } catch (DatabaseIntegrityException e) {
                    LOGGER.debug("Address change failed." + e.getMessage());
                }
                break;
            case REMOVE_USER:
                try {
                    if(userService.removeUser(currentUser)) {
                        isUserLoggedIn = false;
                        currentUser = null;
                    } else {
                        System.out.println("User removal failed.");
                    }
                } catch (DatabaseIntegrityException e) {
                    LOGGER.debug("User removal failed.");
                    System.out.println(e.getMessage());
                }
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
            e.getMessage();
        }
        LOGGER.debug("Returning user from database.");
        return user;
    }

    private void registerNewUser() {
        try {
            userService.createUser();
        } catch (DatabaseIntegrityException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listAllUsers() {
        LOGGER.debug("Listing users form database.");
        userService.findAllUsers().stream()
                .sorted(new UserNameAndAdressComparator())
                .map(User::getName)
                .forEach(System.out::println);
    }

    private void loginView() {
        Console console = System.console();
        System.out.println("==============================");
        System.out.println("|        LOGIN SCREEN        |");
        System.out.println("|    Enter user's email:     ");
        String userEmail = console.readLine();
        password = new String(console.readPassword("|    Enter password:    "));
        try {
            if (authService.isAuthenticated(userService.findUserByEmail(userEmail), new SHA256(), password)) {
                isUserLoggedIn = true;
                currentUser = userService.findUserByEmail(userEmail);
                LOGGER.debug("User logged in.");
            } else {
                System.out.println("User email or password incorrect.");
                LOGGER.debug("User not recognized. Wrong password or email.");
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
